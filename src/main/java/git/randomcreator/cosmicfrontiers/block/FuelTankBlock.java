package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class FuelTankBlock extends SpaceshipComponentBlock implements EntityBlock {
    private final int capacity;

    public FuelTankBlock(Properties properties, float weight, int capacity) {
        super(properties, weight, ComponentType.FUEL_TANK);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelTankBlockEntity(pos, state);
    }

    public static class Small extends FuelTankBlock {
        public Small() {
            super(Properties.of(Material.METAL)
                            .strength(3.0f)
                            .requiresCorrectToolForDrops(),
                    50.0f,  // weight
                    1000    // capacity
            );
        }
    }

    public static class Large extends FuelTankBlock {
        public Large() {
            super(Properties.of(Material.METAL)
                            .strength(3.0f)
                            .requiresCorrectToolForDrops(),
                    150.0f, // weight
                    5000    // capacity
            );
        }
    }
}