package git.randomcreator.cosmicfrontiers.world.structure;

import git.randomcreator.cosmicfrontiers.entity.SpaceshipEntity;
import git.randomcreator.cosmicfrontiers.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Handles teleporting players and ships between space and planets
 */
public class PlanetTeleporter {

    /**
     * Teleport a spaceship and its passengers to a planet
     */
    public static void teleportToPlanet(SpaceshipEntity ship, Planet planet, ServerLevel currentLevel) {
        MinecraftServer server = currentLevel.getServer();
        if (server == null) return;

        ResourceKey<Level> destinationKey = planet.getDestinationDimension();
        ServerLevel destinationLevel = server.getLevel(destinationKey);

        if (destinationLevel == null) return;

        // Find a safe landing spot on the planet surface
        BlockPos landingPos = findLandingSpot(destinationLevel);

        // Teleport ship
        ship.changeDimension(destinationLevel, new PlanetTeleporter.SimpleTeleporter(landingPos));

        // Teleport all passengers
        for (Entity passenger : ship.getPassengers()) {
            if (passenger instanceof ServerPlayer player) {
                player.teleportTo(destinationLevel,
                        landingPos.getX(), landingPos.getY() + 2, landingPos.getZ(),
                        player.getYRot(), player.getXRot());
            }
        }
    }

    /**
     * Teleport a player to space
     */
    public static void teleportToSpace(ServerPlayer player, BlockPos targetPos) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        ServerLevel spaceLevel = server.getLevel(ModDimensions.SPACE_LEVEL_KEY);
        if (spaceLevel == null) return;

        player.teleportTo(spaceLevel,
                targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                player.getYRot(), player.getXRot());
    }

    /**
     * Launch from planet back to space
     */
    public static void launchToSpace(SpaceshipEntity ship, ServerLevel currentLevel) {
        MinecraftServer server = currentLevel.getServer();
        if (server == null) return;

        ServerLevel spaceLevel = server.getLevel(ModDimensions.SPACE_LEVEL_KEY);
        if (spaceLevel == null) return;

        // Find the corresponding planet position in space
        Planet planet = getPlanetForDimension(currentLevel.dimension());
        BlockPos spacePos = planet != null ?
                planet.getCenterPos().offset(0, planet.getRadius() + 50, 0) :
                new BlockPos(0, 200, 0);

        // Teleport ship to space
        ship.changeDimension(spaceLevel, new SimpleTeleporter(spacePos));

        // Teleport passengers
        for (Entity passenger : ship.getPassengers()) {
            if (passenger instanceof ServerPlayer player) {
                player.teleportTo(spaceLevel,
                        spacePos.getX(), spacePos.getY(), spacePos.getZ(),
                        player.getYRot(), player.getXRot());
            }
        }
    }

    /**
     * Find a safe spot to land on a planet
     */
    private static BlockPos findLandingSpot(ServerLevel level) {
        // Start at world spawn and find the highest solid block
        BlockPos spawn = level.getSharedSpawnPos();
        BlockPos.MutableBlockPos pos = spawn.mutable();

        // Search upward for air
        for (int y = spawn.getY(); y < level.getMaxBuildHeight(); y++) {
            pos.setY(y);
            if (level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above())) {
                return pos.immutable();
            }
        }

        return spawn.above(10);
    }

    /**
     * Get the planet that corresponds to a dimension
     */
    private static Planet getPlanetForDimension(ResourceKey<Level> dimension) {
        if (dimension == ModDimensions.MARS_LEVEL_KEY) {
            return PlanetManager.getPlanetByName("Mars");
        } else if (dimension == ModDimensions.MOON_LEVEL_KEY) {
            return PlanetManager.getPlanetByName("Moon");
        } else if (dimension == ModDimensions.VENUS_LEVEL_KEY) {
            return PlanetManager.getPlanetByName("Venus");
        } else if (dimension == ModDimensions.EUROPA_LEVEL_KEY) {
            return PlanetManager.getPlanetByName("Europa");
        }
        return null;
    }

    /**
     * Simple teleporter for dimension changes
     */
    public static class SimpleTeleporter implements net.minecraft.world.level.portal.PortalInfo {
        private final BlockPos targetPos;

        public SimpleTeleporter(BlockPos targetPos) {
            this.targetPos = targetPos;
        }

        public Vec3 pos() {
            return new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        }

        public Vec3 speed() {
            return Vec3.ZERO;
        }

        public float yRot() {
            return 0;
        }

        public float xRot() {
            return 0;
        }
    }
}