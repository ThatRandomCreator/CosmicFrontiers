package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import git.randomcreator.cosmicfrontiers.registry.ModBlockEntities.SpaceshipMediumFuelTankBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, cosmicfrontiers.MODID);

    public static final RegistryObject<Block> SPACESHIP_CONTROLLER = BLOCKS.register("spaceship_controller",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f, 5f)
            ));

    public static final RegistryObject<Block> SPACESHIP_MEDIUM_FUEL_TANK = BLOCKS.register("spaceship_medium_fuel_tank",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f, 5f)
            ));
}
