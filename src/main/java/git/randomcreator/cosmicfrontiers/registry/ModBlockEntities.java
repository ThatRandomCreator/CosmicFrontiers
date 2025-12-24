package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.block.ControllerBlockEntity;
import git.randomcreator.cosmicfrontiers.block.FuelRefineryBlockEntity;
import git.randomcreator.cosmicfrontiers.block.FuelTankBlockEntity;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, cosmicfrontiers.MODID);

    public static final RegistryObject<BlockEntityType<FuelTankBlockEntity>> FUEL_TANK =
            BLOCK_ENTITIES.register("fuel_tank", () ->
                    BlockEntityType.Builder.of(FuelTankBlockEntity::new,
                                    ModBlocks.FUEL_TANK_SMALL.get(),
                                    ModBlocks.FUEL_TANK_LARGE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ControllerBlockEntity>> CONTROLLER =
            BLOCK_ENTITIES.register("controller", () ->
                    BlockEntityType.Builder.of(ControllerBlockEntity::new,
                                    ModBlocks.CONTROLLER_BASIC.get(),
                                    ModBlocks.CONTROLLER_ADVANCED.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY =
            BLOCK_ENTITIES.register("fuel_refinery", () ->
                    BlockEntityType.Builder.of(FuelRefineryBlockEntity::new,
                                    ModBlocks.FUEL_REFINERY.get())
                            .build(null));
}