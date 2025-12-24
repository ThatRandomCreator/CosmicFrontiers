package git.randomcreator.cosmicfrontiers.registry;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import git.randomcreator.cosmicfrontiers.inventory.FuelRefineryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, cosmicfrontiers.MODID);

    public static final RegistryObject<MenuType<FuelRefineryMenu>> FUEL_REFINERY_MENU =
            MENUS.register("fuel_refinery_menu", () ->
                    IForgeMenuType.create(FuelRefineryMenu::new));
}