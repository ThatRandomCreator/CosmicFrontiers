package git.randomcreator.cosmicfrontiers.entity;

import git.randomcreator.cosmicfrontiers.spaceship.SpaceshipData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * The actual flying spaceship entity that moves through space
 */
public class SpaceshipEntity extends Entity {
    private static final EntityDataAccessor<Float> THROTTLE =
            SynchedEntityData.defineId(SpaceshipEntity.class, EntityDataSerializers.FLOAT);

    private SpaceshipData shipData;
    private List<Entity> passengers = new ArrayList<>();
    private Vec3 velocity = Vec3.ZERO;
    private float pitch = 0;
    private float yaw = 0;
    private int fuelConsumptionTick = 0;

    public SpaceshipEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(THROTTLE, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();

        if (shipData == null) return;

        // Get throttle setting (0.0 to 1.0)
        float throttle = this.entityData.get(THROTTLE);

        if (throttle > 0 && shipData.getCurrentFuel() > 0) {
            // Calculate acceleration based on thrust and weight
            float acceleration = (shipData.getTotalThrust() * throttle) / shipData.getTotalWeight();

            // Apply acceleration in the direction ship is facing
            Vec3 forward = getLookAngle();
            velocity = velocity.add(forward.scale(acceleration * 0.01));

            // Consume fuel
            fuelConsumptionTick++;
            if (fuelConsumptionTick >= 20) { // Every second
                int fuelToConsume = (int)(throttle * 10);
                shipData.setCurrentFuel(Math.max(0, shipData.getCurrentFuel() - fuelToConsume));
                fuelConsumptionTick = 0;
            }
        }

        // Apply velocity with drag
        velocity = velocity.scale(0.98); // 2% drag
        setPos(getX() + velocity.x, getY() + velocity.y, getZ() + velocity.z);

        // Rotate ship
        setYRot(yaw);
        setXRot(pitch);

        // Move passengers with ship
        for (Entity passenger : passengers) {
            if (passenger != null && passenger.isAlive()) {
                passenger.setPos(getX(), getY() + 1, getZ());
            }
        }
    }

    public void setThrottle(float throttle) {
        this.entityData.set(THROTTLE, Math.max(0, Math.min(1, throttle)));
    }

    public float getThrottle() {
        return this.entityData.get(THROTTLE);
    }

    public void adjustPitch(float delta) {
        pitch = Math.max(-90, Math.min(90, pitch + delta));
    }

    public void adjustYaw(float delta) {
        yaw = (yaw + delta) % 360;
    }

    public void setShipData(SpaceshipData data) {
        this.shipData = data;
    }

    public SpaceshipData getShipData() {
        return shipData;
    }

    public void addPassenger(Entity entity) {
        if (!passengers.contains(entity)) {
            passengers.add(entity);
        }
    }

    public void removePassenger(Entity entity) {
        passengers.remove(entity);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("ShipData")) {
            shipData = SpaceshipData.deserialize(tag.getCompound("ShipData"));
        }
        velocity = new Vec3(tag.getDouble("VelX"), tag.getDouble("VelY"), tag.getDouble("VelZ"));
        pitch = tag.getFloat("Pitch");
        yaw = tag.getFloat("Yaw");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (shipData != null) {
            tag.put("ShipData", shipData.serialize());
        }
        tag.putDouble("VelX", velocity.x);
        tag.putDouble("VelY", velocity.y);
        tag.putDouble("VelZ", velocity.z);
        tag.putFloat("Pitch", pitch);
        tag.putFloat("Yaw", yaw);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}