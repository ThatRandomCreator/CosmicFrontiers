package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Machine that refines raw materials into rocket fuel
 */
public class FuelRefineryBlock extends Block implements EntityBlock {

    public FuelRefineryBlock() {
        super(Properties.of(Material.METAL)
                .strength(5.0f)
                .requiresCorrectToolForDrops()
                .lightLevel((state) -> 5));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FuelRefineryBlockEntity refinery) {
                // Open refinery GUI
                // TODO: Implement GUI for fuel refining
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelRefineryBlockEntity(pos, state);
    }
}