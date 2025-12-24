package git.randomcreator.cosmicfrontiers.client;

import com.mojang.blaze3d.systems.RenderSystem;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import git.randomcreator.cosmicfrontiers.event.OxygenEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = cosmicfrontiers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OxygenHudOverlay {
    private static final ResourceLocation OXYGEN_BAR =
            new ResourceLocation(cosmicfrontiers.MODID, "textures/gui/oxygen_bar.png");

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id().toString().equals("minecraft:air_level")) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            mc.player.getCapability(OxygenEventHandler.OXYGEN_CAPABILITY).ifPresent(oxygen -> {
                // Only show oxygen bar in vacuum environments
                String dimensionKey = mc.player.level().dimension().location().toString();
                if (!dimensionKey.contains("space") &&
                        !dimensionKey.contains("moon") &&
                        !dimensionKey.contains("mars")) {
                    return;
                }

                GuiGraphics graphics = event.getGuiGraphics();
                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int screenHeight = mc.getWindow().getGuiScaledHeight();

                // Draw oxygen bar in top-left corner
                int x = 10;
                int y = 10;
                int barWidth = 80;
                int barHeight = 10;

                // Background
                graphics.fill(x, y, x + barWidth, y + barHeight, 0x88000000);

                // Oxygen level (blue bar)
                int oxygenWidth = (int) ((oxygen.getOxygen() / (float) oxygen.getMaxOxygen()) * (barWidth - 2));
                int color = oxygen.getOxygen() > 100 ? 0xFF00AAFF : 0xFFFF0000;
                graphics.fill(x + 1, y + 1, x + 1 + oxygenWidth, y + barHeight - 1, color);

                // Border
                graphics.fill(x, y, x + barWidth, y + 1, 0xFFFFFFFF); // Top
                graphics.fill(x, y + barHeight - 1, x + barWidth, y + barHeight, 0xFFFFFFFF); // Bottom
                graphics.fill(x, y, x + 1, y + barHeight, 0xFFFFFFFF); // Left
                graphics.fill(x + barWidth - 1, y, x + barWidth, y + barHeight, 0xFFFFFFFF); // Right

                // Text
                String text = "O₂: " + (int)((oxygen.getOxygen() / (float) oxygen.getMaxOxygen()) * 100) + "%";
                graphics.drawString(mc.font, text, x + barWidth + 5, y + 1, 0xFFFFFFFF, true);

                // Warning if wearing incomplete space suit
                if (!oxygen.hasSpaceSuit() && oxygen.getOxygen() < 200) {
                    graphics.drawString(mc.font, "⚠ NO SPACE SUIT",
                            screenWidth / 2 - 40, y + 15, 0xFFFF0000, true);
                }
            });
        }
    }
}