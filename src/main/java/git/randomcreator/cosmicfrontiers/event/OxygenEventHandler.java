package git.randomcreator.cosmicfrontiers.event;

import git.randomcreator.cosmicfrontiers.capability.OxygenCapability;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = cosmicfrontiers.MODID)
public class OxygenEventHandler {

    public static final Capability<OxygenCapability> OXYGEN_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(OxygenCapability.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation(cosmicfrontiers.MODID, "oxygen"),
                    new OxygenCapabilityProvider()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            event.player.getCapability(OXYGEN_CAPABILITY).ifPresent(oxygen -> {
                oxygen.tick(event.player);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(OXYGEN_CAPABILITY).ifPresent(oldOxygen -> {
                event.getEntity().getCapability(OXYGEN_CAPABILITY).ifPresent(newOxygen -> {
                    newOxygen.deserializeNBT(oldOxygen.serializeNBT());
                });
            });
        }
    }
}