package git.randomcreator.cosmicfrontiers.world.structure;

import git.randomcreator.cosmicfrontiers.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Represents a planet floating in space
 */
public class Planet {
    private final String name;
    private final BlockPos centerPos;
    private final int radius;
    private final BlockState surfaceBlock;
    private final ResourceKey<Level> destinationDimension;
    private final PlanetType type;

    public Planet(String name, BlockPos centerPos, int radius, BlockState surfaceBlock,
                  ResourceKey<Level> destinationDimension, PlanetType type) {
        this.name = name;
        this.centerPos = centerPos;
        this.radius = radius;
        this.surfaceBlock = surfaceBlock;
        this.destinationDimension = destinationDimension;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public BlockPos getCenterPos() {
        return centerPos;
    }

    public int getRadius() {
        return radius;
    }

    public BlockState getSurfaceBlock() {
        return surfaceBlock;
    }

    public ResourceKey<Level> getDestinationDimension() {
        return destinationDimension;
    }

    public PlanetType getType() {
        return type;
    }

    /**
     * Check if a position is within this planet's sphere
     */
    public boolean isWithinPlanet(BlockPos pos) {
        return centerPos.distSqr(pos) <= radius * radius;
    }

    /**
     * Check if a position is near the planet's surface (for landing detection)
     */
    public boolean isNearSurface(BlockPos pos, double threshold) {
        double distance = Math.sqrt(centerPos.distSqr(pos));
        return Math.abs(distance - radius) <= threshold;
    }

    public enum PlanetType {
        ROCKY,      // Mars, Moon
        GAS_GIANT,  // Jupiter, Saturn (can't land)
        ICE,        // Europa, Enceladus
        TERRESTRIAL // Venus, Earth-like
    }

    /**
     * Generate the planet structure in the world
     */
    public void generate(Level level) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double distSq = x * x + y * y + z * z;
                    if (distSq <= radius * radius) {
                        BlockPos pos = centerPos.offset(x, y, z);

                        // Create a solid sphere for the planet
                        double dist = Math.sqrt(distSq);
                        if (dist >= radius - 2) {
                            // Surface layer
                            level.setBlock(pos, surfaceBlock, 3);
                        } else {
                            // Core - use stone or appropriate core material
                            level.setBlock(pos, getCoreBlock(), 3);
                        }
                    }
                }
            }
        }
    }

    private BlockState getCoreBlock() {
        return switch (type) {
            case ROCKY -> Blocks.STONE.defaultBlockState();
            case GAS_GIANT -> Blocks.AIR.defaultBlockState(); // Gas giants are mostly empty
            case ICE -> Blocks.PACKED_ICE.defaultBlockState();
            case TERRESTRIAL -> Blocks.DEEPSLATE.defaultBlockState();
        };
    }
}