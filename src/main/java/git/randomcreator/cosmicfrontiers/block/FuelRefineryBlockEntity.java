package git.randomcreator.cosmicfrontiers.block;

import git.randomcreator.cosmicfrontiers.inventory.FuelRefineryMenu;
import git.randomcreator.cosmicfrontiers.recipe.FuelRefineryRecipe;
import git.randomcreator.cosmicfrontiers.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FuelRefineryBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int progress = 0;
    private int maxProgress = 200;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FuelRefineryBlockEntity.this.progress;
                case 1 -> FuelRefineryBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> FuelRefineryBlockEntity.this.progress = value;
                case 1 -> FuelRefineryBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FUEL_REFINERY.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        SimpleContainer simpleContainer = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            simpleContainer.setItem(i, inventory.getStackInSlot(i));
        }

        Optional<FuelRefineryRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FuelRefineryRecipe.Type.INSTANCE, simpleContainer, level);

        if (recipe.isPresent() && hasRecipe()) {
            progress++;
            setChanged();

            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
        } else {
            progress = 0;
            setChanged();
        }
    }

    private boolean hasRecipe() {
        ItemStack input1 = inventory.getStackInSlot(0);
        ItemStack input2 = inventory.getStackInSlot(1);
        ItemStack output = inventory.getStackInSlot(2);

        return !input1.isEmpty() && !input2.isEmpty()
                && (output.isEmpty() || output.getCount() < output.getMaxStackSize());
    }

    private void craftItem() {
        SimpleContainer simpleContainer = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            simpleContainer.setItem(i, inventory.getStackInSlot(i));
        }

        Optional<FuelRefineryRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(FuelRefineryRecipe.Type.INSTANCE, simpleContainer, level);

        if (recipe.isPresent()) {
            inventory.extractItem(0, 1, false);
            inventory.extractItem(1, 1, false);

            ItemStack result = recipe.get().getResultItem(level.registryAccess());
            inventory.insertItem(2, result.copy(), false);
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fuel Refinery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FuelRefineryMenu(containerId, playerInventory, this, this.data);
    }
}