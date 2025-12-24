package git.randomcreator.cosmicfrontiers.inventory;

import git.randomcreator.cosmicfrontiers.block.FuelRefineryBlockEntity;
import git.randomcreator.cosmicfrontiers.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class FuelRefineryMenu extends AbstractContainerMenu {
    private final FuelRefineryBlockEntity blockEntity;
    private final ContainerData data;

    public FuelRefineryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory,
                (FuelRefineryBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(2));
    }

    public FuelRefineryMenu(int containerId, Inventory playerInventory, FuelRefineryBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.FUEL_REFINERY_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.data = data;

        // Add block entity slots
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 44, 35)); // Input 1
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 62, 35)); // Input 2
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 116, 35)); // Output
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 3, 8, 62)); // Energy/Catalyst 1
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 4, 26, 62)); // Energy/Catalyst 2

        // Add player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Add player hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        this.addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            if (index < 5) {
                // Moving from block entity to player inventory
                if (!this.moveItemStackTo(slotStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from player inventory to block entity
                if (!this.moveItemStackTo(slotStack, 0, 5, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null &&
                blockEntity.getLevel() != null &&
                player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5,
                        blockEntity.getBlockPos().getY() + 0.5,
                        blockEntity.getBlockPos().getZ() + 0.5) <= 64.0;
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }
}