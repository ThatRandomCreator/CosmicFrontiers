package git.randomcreator.cosmicfrontiers.registry.ModBlockEntities;

import com.google.common.collect.Sets;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import git.randomcreator.cosmicfrontiers.registry.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, cosmicfrontiers.MODID);

    public static final RegistryObject<BlockEntityType<SpaceshipMediumFuelTankBlockEntity>> SPACESHIP_MEDIUM_FUEL_TANK =
            BLOCK_ENTITIES.register("spaceship_medium_fuel_tank",
                    () -> BlockEntityType.Builder.of(SpaceshipMediumFuelTankBlockEntity::new, ModBlocks.SPACESHIP_MEDIUM_FUEL_TANK.get())
                            .build(null)
            );
}
