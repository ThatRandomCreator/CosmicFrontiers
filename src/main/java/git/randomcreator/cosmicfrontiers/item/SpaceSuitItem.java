package git.randomcreator.cosmicfrontiers.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class SpaceSuitItem extends ArmorItem {

    public SpaceSuitItem(Type type, Properties properties) {
        super(SPACE_SUIT_MATERIAL, type, properties);
    }

    public static final ArmorMaterial SPACE_SUIT_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 200;
                case CHESTPLATE -> 300;
                case LEGGINGS -> 250;
                case BOOTS -> 200;
                default -> 0;
            };
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 2;
                case CHESTPLATE -> 5;
                case LEGGINGS -> 4;
                case BOOTS -> 2;
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_IRON;
        }

        @Override
        public Ingredient getRepairIngredient() {
            // TODO: Add custom repair material
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "space_suit";
        }

        @Override
        public float getToughness() {
            return 1.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };

    public static class Helmet extends SpaceSuitItem {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }
    }

    public static class Chestplate extends SpaceSuitItem {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }
    }

    public static class Leggings extends SpaceSuitItem {
        public Leggings() {
            super(Type.LEGGINGS, new Properties());
        }
    }

    public static class Boots extends SpaceSuitItem {
        public Boots() {
            super(Type.BOOTS, new Properties());
        }
    }
}