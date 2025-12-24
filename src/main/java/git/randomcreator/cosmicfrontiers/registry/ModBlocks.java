package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.block.*;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, cosmicfrontiers.MODID);

    // Fuel Tanks
    public static final RegistryObject<Block> FUEL_TANK_SMALL =
            BLOCKS.register("fuel_tank_small", FuelTankBlock.Small::new);

    public static final RegistryObject<Block> FUEL_TANK_LARGE =
            BLOCKS.register("fuel_tank_large", FuelTankBlock.Large::new);

    // Engines
    public static final RegistryObject<Block> ENGINE_SMALL =
            BLOCKS.register("engine_small", EngineBlock.Small::new);

    public static final RegistryObject<Block> ENGINE_LARGE =
            BLOCKS.register("engine_large", EngineBlock.Large::new);

    // Controllers
    public static final RegistryObject<Block> CONTROLLER_BASIC =
            BLOCKS.register("controller_basic", ControllerBlock.Basic::new);

    public static final RegistryObject<Block> CONTROLLER_ADVANCED =
            BLOCKS.register("controller_advanced", ControllerBlock.Advanced::new);

    // Ship Components
    public static final RegistryObject<Block> SHIP_SEAT =
            BLOCKS.register("ship_seat", SeatBlock::new);

    public static final RegistryObject<Block> HULL_BLOCK =
            BLOCKS.register("hull_block", HullBlock::new);

    public static final RegistryObject<Block> HULL_GLASS =
            BLOCKS.register("hull_glass", HullGlassBlock::new);

    public static final RegistryObject<Block> AIRLOCK =
            BLOCKS.register("airlock", AirlockBlock::new);

    // Fuel Production
    public static final RegistryObject<Block> FUEL_REFINERY =
            BLOCKS.register("fuel_refinery", FuelRefineryBlock::new);
}