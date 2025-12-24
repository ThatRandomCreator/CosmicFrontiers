package git.randomcreator.cosmicfrontiers.fluid;

import git.randomcreator.cosmicfrontiers.registry.ModBlocks;
import git.randomcreator.cosmicfrontiers.registry.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class RocketFuelFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_ROCKET_FUEL.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.SOURCE_ROCKET_FUEL.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.ROCKET_FUEL_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        // Handle block interaction
    }

    @Override
    protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) {
        return 4;
    }

    @Override
    protected int getDropOff(net.minecraft.world.level.LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(net.minecraft.world.level.LevelReader level) {
        return 10;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public net.minecraftforge.fluids.FluidType getFluidType() {
        return ModFluids.ROCKET_FUEL_FLUID_TYPE.get();
    }

    public static class Source extends RocketFuelFluid {
        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return true;
        }
    }

    public static class Flowing extends RocketFuelFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, net.minecraft.world.level.block.state.FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return false;
        }
    }
}

// Similar implementations for Liquid Oxygen
abstract class LiquidOxygenFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_LIQUID_OXYGEN.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.SOURCE_LIQUID_OXYGEN.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.LIQUID_OXYGEN_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) {
        return 4;
    }

    @Override
    protected int getDropOff(net.minecraft.world.level.LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(net.minecraft.world.level.LevelReader level) {
        return 10;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public net.minecraftforge.fluids.FluidType getFluidType() {
        return ModFluids.LIQUID_OXYGEN_FLUID_TYPE.get();
    }

    public static class Source extends LiquidOxygenFluid {
        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return true;
        }
    }

    public static class Flowing extends LiquidOxygenFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, net.minecraft.world.level.block.state.FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return false;
        }
    }
}

// Similar implementation for Liquid Hydrogen
abstract class LiquidHydrogenFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_LIQUID_HYDROGEN.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.SOURCE_LIQUID_HYDROGEN.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.LIQUID_HYDROGEN_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) {
        return 4;
    }

    @Override
    protected int getDropOff(net.minecraft.world.level.LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(net.minecraft.world.level.LevelReader level) {
        return 10;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public net.minecraftforge.fluids.FluidType getFluidType() {
        return ModFluids.LIQUID_HYDROGEN_FLUID_TYPE.get();
    }

    public static class Source extends LiquidHydrogenFluid {
        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return true;
        }
    }

    public static class Flowing extends LiquidHydrogenFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, net.minecraft.world.level.block.state.FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(net.minecraft.world.level.block.state.FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(net.minecraft.world.level.block.state.FluidState state) {
            return false;
        }
    }
}