package git.randomcreator.cosmicfrontiers.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

/**
 * Tracks player oxygen levels in space
 */
public class OxygenCapability {
    private int oxygen = 300; // 15 seconds of air (20 ticks per second)
    private final int maxOxygen = 300;
    private boolean hasSpaceSuit = false;

    public int getOxygen() {
        return oxygen;
    }

    public int getMaxOxygen() {
        return maxOxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = Math.max(0, Math.min(maxOxygen, oxygen));
    }

    public void consumeOxygen(int amount) {
        oxygen = Math.max(0, oxygen - amount);
    }

    public void replenishOxygen(int amount) {
        oxygen = Math.min(maxOxygen, oxygen + amount);
    }

    public boolean hasSpaceSuit() {
        return hasSpaceSuit;
    }

    public void setHasSpaceSuit(boolean hasSpaceSuit) {
        this.hasSpaceSuit = hasSpaceSuit;
    }

    public boolean needsOxygen() {
        return oxygen <= 0;
    }

    public void tick(Player player) {
        // Check if player is wearing full space suit
        updateSpaceSuitStatus(player);

        // Check if player is in space or on a planet without atmosphere
        if (isInVacuum(player)) {
            if (hasSpaceSuit) {
                // Space suit provides oxygen
                replenishOxygen(1);
            } else {
                // Losing oxygen rapidly in vacuum
                consumeOxygen(2);

                // Damage player if out of oxygen
                if (needsOxygen() && player.tickCount % 20 == 0) {
                    player.hurt(player.damageSources().generic(), 2.0F);
                }
            }
        } else {
            // In a safe area (ship with airlock, planet with atmosphere)
            replenishOxygen(2);
        }
    }

    private void updateSpaceSuitStatus(Player player) {
        // Check if player has all 4 pieces of space suit equipped
        boolean helmet = false, chest = false, legs = false, boots = false;

        if (player.getInventory().getArmor(3).getItem().toString().contains("space_suit_helmet")) {
            helmet = true;
        }
        if (player.getInventory().getArmor(2).getItem().toString().contains("space_suit_chestplate")) {
            chest = true;
        }
        if (player.getInventory().getArmor(1).getItem().toString().contains("space_suit_leggings")) {
            legs = true;
        }
        if (player.getInventory().getArmor(0).getItem().toString().contains("space_suit_boots")) {
            boots = true;
        }

        hasSpaceSuit = helmet && chest && legs && boots;
    }

    private boolean isInVacuum(Player player) {
        // Check if player is in space dimension
        String dimensionKey = player.level().dimension().location().toString();

        // Space and airless planets don't have oxygen
        return dimensionKey.contains("space") ||
                dimensionKey.contains("moon") ||
                dimensionKey.contains("mars");
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Oxygen", oxygen);
        tag.putBoolean("HasSpaceSuit", hasSpaceSuit);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        oxygen = tag.getInt("Oxygen");
        hasSpaceSuit = tag.getBoolean("HasSpaceSuit");
    }
}