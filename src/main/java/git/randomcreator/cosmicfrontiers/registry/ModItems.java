package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, cosmicfrontiers.MODID);

    public static final RegistryObject<Item> SPACESHIP_CONTROLLER = ITEMS.register("spaceship_controller",
            () -> new BlockItem(ModBlocks.SPACESHIP_CONTROLLER.get(), new Item.Properties()
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> SPACESHIP_MEDIUM_FUEL_TANK = ITEMS.register("spaceship_medium_fuel_tank",
            () -> new BlockItem(ModBlocks.SPACESHIP_MEDIUM_FUEL_TANK.get(), new Item.Properties()
                    .stacksTo(64)
            ));
}
