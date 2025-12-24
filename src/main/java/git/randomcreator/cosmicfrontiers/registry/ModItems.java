package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import git.randomcreator.cosmicfrontiers.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, cosmicfrontiers.MODID);

    // Block Items for all blocks
    public static final RegistryObject<Item> FUEL_TANK_SMALL_ITEM =
            ITEMS.register("fuel_tank_small", () ->
                    new BlockItem(ModBlocks.FUEL_TANK_SMALL.get(), new Item.Properties()));

    public static final RegistryObject<Item> FUEL_TANK_LARGE_ITEM =
            ITEMS.register("fuel_tank_large", () ->
                    new BlockItem(ModBlocks.FUEL_TANK_LARGE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENGINE_SMALL_ITEM =
            ITEMS.register("engine_small", () ->
                    new BlockItem(ModBlocks.ENGINE_SMALL.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENGINE_LARGE_ITEM =
            ITEMS.register("engine_large", () ->
                    new BlockItem(ModBlocks.ENGINE_LARGE.get(), new Item.Properties()));

    public static final RegistryObject<Item> CONTROLLER_BASIC_ITEM =
            ITEMS.register("controller_basic", () ->
                    new BlockItem(ModBlocks.CONTROLLER_BASIC.get(), new Item.Properties()));

    public static final RegistryObject<Item> CONTROLLER_ADVANCED_ITEM =
            ITEMS.register("controller_advanced", () ->
                    new BlockItem(ModBlocks.CONTROLLER_ADVANCED.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHIP_SEAT_ITEM =
            ITEMS.register("ship_seat", () ->
                    new BlockItem(ModBlocks.SHIP_SEAT.get(), new Item.Properties()));

    public static final RegistryObject<Item> HULL_BLOCK_ITEM =
            ITEMS.register("hull_block", () ->
                    new BlockItem(ModBlocks.HULL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> HULL_GLASS_ITEM =
            ITEMS.register("hull_glass", () ->
                    new BlockItem(ModBlocks.HULL_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> AIRLOCK_ITEM =
            ITEMS.register("airlock", () ->
                    new BlockItem(ModBlocks.AIRLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> FUEL_REFINERY_ITEM =
            ITEMS.register("fuel_refinery", () ->
                    new BlockItem(ModBlocks.FUEL_REFINERY.get(), new Item.Properties()));

    // Space Suit Armor
    public static final RegistryObject<Item> SPACE_SUIT_HELMET =
            ITEMS.register("space_suit_helmet", SpaceSuitItem.Helmet::new);

    public static final RegistryObject<Item> SPACE_SUIT_CHESTPLATE =
            ITEMS.register("space_suit_chestplate", SpaceSuitItem.Chestplate::new);

    public static final RegistryObject<Item> SPACE_SUIT_LEGGINGS =
            ITEMS.register("space_suit_leggings", SpaceSuitItem.Leggings::new);

    public static final RegistryObject<Item> SPACE_SUIT_BOOTS =
            ITEMS.register("space_suit_boots", SpaceSuitItem.Boots::new);

    // Fuel Components
    public static final RegistryObject<Item> FUEL_COMPONENT =
            ITEMS.register("fuel_component", () ->
                    new FuelComponentItem(new Item.Properties()));

    public static final RegistryObject<Item> ROCKET_FUEL_BUCKET =
            ITEMS.register("rocket_fuel_bucket", () ->
                    new RocketFuelBucketItem(new Item.Properties()));

    public static final RegistryObject<Item> LIQUID_OXYGEN_BUCKET =
            ITEMS.register("liquid_oxygen_bucket", LiquidOxygenBucketItem::new);

    public static final RegistryObject<Item> LIQUID_HYDROGEN_BUCKET =
            ITEMS.register("liquid_hydrogen_bucket", LiquidHydrogenBucketItem::new);
}