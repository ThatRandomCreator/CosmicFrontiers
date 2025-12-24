package git.randomcreator.cosmicfrontiers;

import git.randomcreator.cosmicfrontiers.registry.ModBlockEntities.ModBlockEntities;
import git.randomcreator.cosmicfrontiers.registry.ModBlocks;
import git.randomcreator.cosmicfrontiers.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;;

@Mod(cosmicfrontiers.MODID)
public class cosmicfrontiers {
    public static final String MODID = "cosmicfrontiers";

    public cosmicfrontiers() {
        @SuppressWarnings("removal")
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
    }
}