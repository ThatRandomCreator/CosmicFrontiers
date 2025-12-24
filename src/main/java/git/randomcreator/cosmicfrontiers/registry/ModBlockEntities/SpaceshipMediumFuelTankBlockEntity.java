package git.randomcreator.cosmicfrontiers.registry.ModBlockEntities;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class SpaceshipMediumFuelTankBlockEntity extends BlockEntity {
    public SpaceshipMediumFuelTankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPACESHIP_MEDIUM_FUEL_TANK.get(), pos, state);
    }
    LazyOptional<IItemHandler> inventoryHandlerLazyOptional;


    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryHandlerLazyOptional.invalidate();
    }

    /*@Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        CompoundTag cosmicfrontiersmodData = nbt.getCompound(cosmicfrontiers.MODID);
        this.counter = cosmicfrontiersmodData.getInt("Counttere");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        var cosmicfrontiersmodData = new CompoundTag();
        cosmicfrontiersmodData.putInt("Counter", this.counter);
        nbt.put(cosmicfrontiers.MODID, cosmicfrontiersmodData);
    }

    public int incrementCounter() {
        return ++this.counter;
    }

    public int getCounter() {
        return this.counter;
    }*/


}
