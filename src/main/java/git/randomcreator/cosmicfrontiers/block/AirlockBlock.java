package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class AirlockBlock extends SpaceshipComponentBlock {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty SEALED = BooleanProperty.create("sealed");

    public AirlockBlock() {
        super(Properties.of(Material.METAL)
                        .strength(4.0f)
                        .requiresCorrectToolForDrops()
                        .noOcclusion(),
                40.0f,  // weight
                ComponentType.AIR_LOCK
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(OPEN, false)
                .setValue(SEALED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            boolean isOpen = state.getValue(OPEN);
            level.setBlock(pos, state.setValue(OPEN, !isOpen), 3);
            // Play sound effect
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN, SEALED);
    }

    /**
     * Check if airlock should be sealed (in space or dangerous atmosphere)
     */
    public boolean shouldSeal(Level level, BlockPos pos) {
        // Check if in space dimension or oxygen-less environment
        // TODO: Implement atmosphere checking
        return false;
    }
}