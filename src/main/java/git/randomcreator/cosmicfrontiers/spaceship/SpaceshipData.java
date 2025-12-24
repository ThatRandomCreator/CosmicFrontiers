package git.randomcreator.cosmicfrontiers.spaceship;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all data about an assembled spaceship
 */
public class SpaceshipData {
    private float totalWeight;
    private float totalThrust;
    private int totalFuelCapacity;
    private int currentFuel;
    private List<BlockPos> shipBlocks;
    private BlockPos controllerPos;
    private int seatCount;
    private boolean hasAirlock;

    public SpaceshipData() {
        this.shipBlocks = new ArrayList<>();
    }

    // Getters and setters
    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public float getTotalThrust() {
        return totalThrust;
    }

    public void setTotalThrust(float totalThrust) {
        this.totalThrust = totalThrust;
    }

    public int getTotalFuelCapacity() {
        return totalFuelCapacity;
    }

    public void setTotalFuelCapacity(int totalFuelCapacity) {
        this.totalFuelCapacity = totalFuelCapacity;
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }

    public List<BlockPos> getShipBlocks() {
        return shipBlocks;
    }

    public void addShipBlock(BlockPos pos) {
        shipBlocks.add(pos);
    }

    public BlockPos getControllerPos() {
        return controllerPos;
    }

    public void setControllerPos(BlockPos controllerPos) {
        this.controllerPos = controllerPos;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public boolean hasAirlock() {
        return hasAirlock;
    }

    public void setHasAirlock(boolean hasAirlock) {
        this.hasAirlock = hasAirlock;
    }

    /**
     * Check if ship can launch based on weight vs thrust ratio
     */
    public boolean canLaunch() {
        return totalThrust >= totalWeight * 1.5f && currentFuel > 0;
    }

    /**
     * Get thrust to weight ratio
     */
    public float getThrustToWeightRatio() {
        if (totalWeight == 0) return 0;
        return totalThrust / totalWeight;
    }

    // Serialization
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("TotalWeight", totalWeight);
        tag.putFloat("TotalThrust", totalThrust);
        tag.putInt("TotalFuelCapacity", totalFuelCapacity);
        tag.putInt("CurrentFuel", currentFuel);
        tag.putInt("SeatCount", seatCount);
        tag.putBoolean("HasAirlock", hasAirlock);

        if (controllerPos != null) {
            tag.put("ControllerPos", NbtUtils.writeBlockPos(controllerPos));
        }

        ListTag blocksList = new ListTag();
        for (BlockPos pos : shipBlocks) {
            blocksList.add(NbtUtils.writeBlockPos(pos));
        }
        tag.put("ShipBlocks", blocksList);

        return tag;
    }

    public static SpaceshipData deserialize(CompoundTag tag) {
        SpaceshipData data = new SpaceshipData();
        data.totalWeight = tag.getFloat("TotalWeight");
        data.totalThrust = tag.getFloat("TotalThrust");
        data.totalFuelCapacity = tag.getInt("TotalFuelCapacity");
        data.currentFuel = tag.getInt("CurrentFuel");
        data.seatCount = tag.getInt("SeatCount");
        data.hasAirlock = tag.getBoolean("HasAirlock");

        if (tag.contains("ControllerPos")) {
            data.controllerPos = NbtUtils.readBlockPos(tag.getCompound("ControllerPos"));
        }

        ListTag blocksList = tag.getList("ShipBlocks", 10);
        for (int i = 0; i < blocksList.size(); i++) {
            data.shipBlocks.add(NbtUtils.readBlockPos(blocksList.getCompound(i)));
        }

        return data;
    }
}