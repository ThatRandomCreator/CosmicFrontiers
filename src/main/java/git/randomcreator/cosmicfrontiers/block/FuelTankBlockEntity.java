package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import git.randomcreator.cosmicfrontiers.registry.ModBlockEntities;

public class FuelTankBlockEntity extends BlockEntity {
    private int fuelAmount = 0;
    private int maxCapacity;

    public FuelTankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FUEL_TANK.get(), pos, state);
        if (state.getBlock() instanceof FuelTankBlock tank) {
            this.maxCapacity = tank.getCapacity();
        }
    }

    public int getFuelAmount() {
        return fuelAmount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean addFuel(int amount) {
        if (fuelAmount + amount <= maxCapacity) {
            fuelAmount += amount;
            setChanged();
            return true;
        }
        return false;
    }

    public int extractFuel(int amount) {
        int extracted = Math.min(amount, fuelAmount);
        fuelAmount -= extracted;
        setChanged();
        return extracted;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("FuelAmount", fuelAmount);
        tag.putInt("MaxCapacity", maxCapacity);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fuelAmount = tag.getInt("FuelAmount");
        maxCapacity = tag.getInt("MaxCapacity");
    }
}