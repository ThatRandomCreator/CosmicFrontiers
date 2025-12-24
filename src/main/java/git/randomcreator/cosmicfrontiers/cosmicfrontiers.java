package git.randomcreator.cosmicfrontiers;

import git.randomcreator.cosmicfrontiers.fluid.ModFluids;
import git.randomcreator.cosmicfrontiers.registry.ModBlockEntities;
import git.randomcreator.cosmicfrontiers.registry.ModBlocks;
import git.randomcreator.cosmicfrontiers.registry.ModItems;
import git.randomcreator.cosmicfrontiers.registry.ModMenuTypes;
import git.randomcreator.cosmicfrontiers.world.dimension.ModDimensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(cosmicfrontiers.MODID)
public class cosmicfrontiers {
    public static final String MODID = "cosmicfrontiers";

    public cosmicfrontiers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all deferred registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);

        // Register fluid systems
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);

        // Common setup
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register dimensions
            ModDimensions.register();
        });
    }
}