package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class HullBlock extends SpaceshipComponentBlock {

    public HullBlock() {
        super(Properties.of(Material.METAL)
                        .strength(5.0f)
                        .requiresCorrectToolForDrops(),
                25.0f,  // weight
                ComponentType.HULL
        );
    }
}

/**
 * Glass hull block for visibility
 */
class HullGlassBlock extends SpaceshipComponentBlock {

    public HullGlassBlock() {
        super(Properties.of(Material.GLASS)
                        .strength(2.0f)
                        .noOcclusion(),
                15.0f,  // weight (lighter than regular hull)
                ComponentType.HULL_GLASS
        );
    }

    @Override
    public float getShadeBrightness(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        return true;
    }
}