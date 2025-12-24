package git.randomcreator.cosmicfrontiers.world.structure;

import git.randomcreator.cosmicfrontiers.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all planets in the space dimension
 */
public class PlanetManager {
    private static final List<Planet> PLANETS = new ArrayList<>();

    static {
        // Initialize default planets
        // These spawn at specific coordinates in space

        // Moon - closest, small, gray
        PLANETS.add(new Planet(
                "Moon",
                new BlockPos(1000, 128, 1000),
                50,
                Blocks.STONE.defaultBlockState(),
                ModDimensions.MOON_LEVEL_KEY,
                Planet.PlanetType.ROCKY
        ));

        // Mars - medium distance, red planet
        PLANETS.add(new Planet(
                "Mars",
                new BlockPos(3000, 128, 500),
                80,
                Blocks.RED_SANDSTONE.defaultBlockState(),
                ModDimensions.MARS_LEVEL_KEY,
                Planet.PlanetType.ROCKY
        ));

        // Venus - hot, yellow/orange
        PLANETS.add(new Planet(
                "Venus",
                new BlockPos(-2000, 128, -1500),
                75,
                Blocks.ORANGE_TERRACOTTA.defaultBlockState(),
                ModDimensions.VENUS_LEVEL_KEY,
                Planet.PlanetType.TERRESTRIAL
        ));

        // Europa (Jupiter's moon) - icy
        PLANETS.add(new Planet(
                "Europa",
                new BlockPos(5000, 128, -3000),
                60,
                Blocks.ICE.defaultBlockState(),
                ModDimensions.EUROPA_LEVEL_KEY,
                Planet.PlanetType.ICE
        ));
    }

    /**
     * Get all planets
     */
    public static List<Planet> getPlanets() {
        return new ArrayList<>(PLANETS);
    }

    /**
     * Find the nearest planet to a position
     */
    public static Planet getNearestPlanet(BlockPos pos) {
        Planet nearest = null;
        double minDistSq = Double.MAX_VALUE;

        for (Planet planet : PLANETS) {
            double distSq = planet.getCenterPos().distSqr(pos);
            if (distSq < minDistSq) {
                minDistSq = distSq;
                nearest = planet;
            }
        }

        return nearest;
    }

    /**
     * Check if a position is within any planet
     */
    public static Planet getPlanetAt(BlockPos pos) {
        for (Planet planet : PLANETS) {
            if (planet.isWithinPlanet(pos)) {
                return planet;
            }
        }
        return null;
    }

    /**
     * Generate all planets in the space dimension
     */
    public static void generatePlanets(Level level) {
        if (level.dimension() == ModDimensions.SPACE_LEVEL_KEY) {
            for (Planet planet : PLANETS) {
                planet.generate(level);
            }
        }
    }

    /**
     * Get planet by name
     */
    public static Planet getPlanetByName(String name) {
        for (Planet planet : PLANETS) {
            if (planet.getName().equalsIgnoreCase(name)) {
                return planet;
            }
        }
        return null;
    }

    /**
     * Check if spaceship is close enough to land on a planet
     */
    public static Planet getPlanetForLanding(BlockPos shipPos) {
        for (Planet planet : PLANETS) {
            if (planet.isNearSurface(shipPos, 20.0)) { // Within 20 blocks of surface
                return planet;
            }
        }
        return null;
    }
}