package git.randomcreator.cosmicfrontiers.spaceship;

import git.randomcreator.cosmicfrontiers.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

/**
 * Scans and validates spaceship structures
 */
public class SpaceshipScanner {

    /**
     * Scans connected blocks starting from a controller
     * Uses flood-fill algorithm to find all connected ship blocks
     */
    public static SpaceshipData scanShip(Level level, BlockPos controllerPos, int maxRange) {
        SpaceshipData data = new SpaceshipData();
        data.setControllerPos(controllerPos);

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> toVisit = new LinkedList<>();
        toVisit.add(controllerPos);
        visited.add(controllerPos);

        float totalWeight = 0;
        float totalThrust = 0;
        int totalFuelCapacity = 0;
        int currentFuel = 0;
        int seatCount = 0;
        boolean hasAirlock = false;

        // Flood fill to find all connected blocks
        while (!toVisit.isEmpty()) {
            BlockPos pos = toVisit.poll();

            // Check range limit
            if (pos.distManhattan(controllerPos) > maxRange) {
                continue;
            }

            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            // Only process spaceship component blocks
            if (block instanceof SpaceshipComponentBlock component) {
                data.addShipBlock(pos);
                totalWeight += component.getWeight();

                // Process specific component types
                switch (component.getComponentType()) {
                    case FUEL_TANK:
                        if (block instanceof FuelTankBlock tank) {
                            totalFuelCapacity += tank.getCapacity();
                            BlockEntity be = level.getBlockEntity(pos);
                            if (be instanceof FuelTankBlockEntity tankEntity) {
                                currentFuel += tankEntity.getFuelAmount();
                            }
                        }
                        break;

                    case ENGINE:
                        if (block instanceof EngineBlock engine) {
                            totalThrust += engine.getThrust();
                        }
                        break;

                    case SEAT:
                        seatCount++;
                        break;

                    case AIR_LOCK:
                        hasAirlock = true;
                        break;
                }

                // Add neighboring blocks to queue
                for (BlockPos neighbor : getNeighbors(pos)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        toVisit.add(neighbor);
                    }
                }
            }
        }

        // Set all calculated values
        data.setTotalWeight(totalWeight);
        data.setTotalThrust(totalThrust);
        data.setTotalFuelCapacity(totalFuelCapacity);
        data.setCurrentFuel(currentFuel);
        data.setSeatCount(seatCount);
        data.setHasAirlock(hasAirlock);

        return data;
    }

    /**
     * Get all 6 neighboring positions (up, down, north, south, east, west)
     */
    private static List<BlockPos> getNeighbors(BlockPos pos) {
        return Arrays.asList(
                pos.above(),
                pos.below(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        );
    }

    /**
     * Validates if a ship structure is valid for launch
     */
    public static ValidationResult validateShip(SpaceshipData data) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        // Check for controller
        if (data.getControllerPos() == null) {
            errors.add("No controller found");
        }

        // Check for engines
        if (data.getTotalThrust() == 0) {
            errors.add("No engines detected");
        }

        // Check thrust to weight ratio
        if (!data.canLaunch()) {
            errors.add("Insufficient thrust (need at least 1.5x weight)");
            errors.add(String.format("Current ratio: %.2f (need: 1.50)", data.getThrustToWeightRatio()));
        }

        // Check for fuel
        if (data.getCurrentFuel() == 0) {
            errors.add("No fuel in tanks");
        }

        // Warnings
        if (data.getSeatCount() == 0) {
            warnings.add("No seats - crew will have to stand!");
        }

        if (!data.hasAirlock()) {
            warnings.add("No airlock - cannot exit safely in space");
        }

        if (data.getShipBlocks().isEmpty()) {
            errors.add("No ship structure detected");
        }

        return new ValidationResult(errors.isEmpty(), errors, warnings);
    }

    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;
        private final List<String> warnings;

        public ValidationResult(boolean valid, List<String> errors, List<String> warnings) {
            this.valid = valid;
            this.errors = errors;
            this.warnings = warnings;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }

        public List<String> getWarnings() {
            return warnings;
        }
    }
}