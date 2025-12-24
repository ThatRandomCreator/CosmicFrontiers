package git.randomcreator.cosmicfrontiers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import git.randomcreator.cosmicfrontiers.inventory.FuelRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FuelRefineryScreen extends AbstractContainerScreen<FuelRefineryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("cosmicfrontiers", "textures/gui/fuel_refinery.png");

    public FuelRefineryScreen(FuelRefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Draw progress bar
        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();

        if (maxProgress > 0) {
            int progressPixels = (progress * 24) / maxProgress;
            graphics.blit(TEXTURE, x + 79, y + 34, 176, 0, progressPixels, 17);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94, 0x404040, false);

        // Show progress percentage
        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        if (maxProgress > 0) {
            int percentage = (progress * 100) / maxProgress;
            graphics.drawString(this.font, percentage + "%", 85, 55, 0x404040, false);
        }
    }
}