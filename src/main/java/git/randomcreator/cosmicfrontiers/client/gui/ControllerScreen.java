package git.randomcreator.cosmicfrontiers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import git.randomcreator.cosmicfrontiers.spaceship.SpaceshipData;
import git.randomcreator.cosmicfrontiers.spaceship.SpaceshipScanner;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ControllerScreen extends Screen {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("cosmicfrontiers", "textures/gui/controller.png");

    private final SpaceshipData shipData;
    private boolean assembled = false;
    private SpaceshipScanner.ValidationResult validation;

    private int leftPos;
    private int topPos;
    private final int imageWidth = 176;
    private final int imageHeight = 166;

    public ControllerScreen(SpaceshipData shipData) {
        super(Component.literal("Ship Controller"));
        this.shipData = shipData;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        // Assemble/Disassemble button
        this.addRenderableWidget(Button.builder(
                        Component.literal(assembled ? "Disassemble" : "Assemble"),
                        button -> toggleAssembly())
                .bounds(leftPos + 10, topPos + 20, 80, 20)
                .build());

        // Launch button
        this.addRenderableWidget(Button.builder(
                        Component.literal("Launch"),
                        button -> launchShip())
                .bounds(leftPos + 10, topPos + 45, 80, 20)
                .build());

        // Validate button
        this.addRenderableWidget(Button.builder(
                        Component.literal("Validate"),
                        button -> validateShip())
                .bounds(leftPos + 10, topPos + 70, 80, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        // Draw GUI background
        RenderSystem.setShaderTexture(0, TEXTURE);
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        super.render(graphics, mouseX, mouseY, partialTick);

        // Draw ship stats
        if (shipData != null && assembled) {
            int textX = leftPos + 100;
            int textY = topPos + 20;

            graphics.drawString(this.font, "Ship Statistics:", textX, textY, 0x404040, false);
            textY += 12;
            graphics.drawString(this.font,
                    String.format("Weight: %.1f kg", shipData.getTotalWeight()),
                    textX, textY, 0x404040, false);
            textY += 10;
            graphics.drawString(this.font,
                    String.format("Thrust: %.1f N", shipData.getTotalThrust()),
                    textX, textY, 0x404040, false);
            textY += 10;
            graphics.drawString(this.font,
                    String.format("T/W Ratio: %.2f", shipData.getThrustToWeightRatio()),
                    textX, textY, shipData.canLaunch() ? 0x00AA00 : 0xAA0000, false);
            textY += 10;
            graphics.drawString(this.font,
                    String.format("Fuel: %d / %d", shipData.getCurrentFuel(), shipData.getTotalFuelCapacity()),
                    textX, textY, 0x404040, false);
            textY += 10;
            graphics.drawString(this.font,
                    String.format("Seats: %d", shipData.getSeatCount()),
                    textX, textY, 0x404040, false);
            textY += 10;
            graphics.drawString(this.font,
                    String.format("Airlock: %s", shipData.hasAirlock() ? "Yes" : "No"),
                    textX, textY, shipData.hasAirlock() ? 0x00AA00 : 0xAA0000, false);
        }

        // Draw validation results
        if (validation != null) {
            int valY = topPos + 100;

            if (!validation.isValid()) {
                graphics.drawString(this.font, "Errors:", leftPos + 10, valY, 0xAA0000, false);
                valY += 10;
                for (String error : validation.getErrors()) {
                    graphics.drawString(this.font, "- " + error, leftPos + 15, valY, 0xAA0000, false);
                    valY += 10;
                }
            }

            if (!validation.getWarnings().isEmpty()) {
                graphics.drawString(this.font, "Warnings:", leftPos + 10, valY, 0xFFAA00, false);
                valY += 10;
                for (String warning : validation.getWarnings()) {
                    graphics.drawString(this.font, "- " + warning, leftPos + 15, valY, 0xFFAA00, false);
                    valY += 10;
                }
            }

            if (validation.isValid()) {
                graphics.drawString(this.font, "Ship ready for launch!", leftPos + 10, valY, 0x00AA00, false);
            }
        }
    }

    private void toggleAssembly() {
        assembled = !assembled;
        if (assembled) {
            // Send packet to server to assemble ship
            // TODO: Implement networking
        } else {
            // Send packet to server to disassemble
            // TODO: Implement networking
        }
        this.init(); // Refresh buttons
    }

    private void launchShip() {
        if (assembled && shipData != null && shipData.canLaunch()) {
            // Send packet to server to launch ship
            // TODO: Implement networking and entity spawning
            this.onClose();
        }
    }

    private void validateShip() {
        if (shipData != null) {
            validation = SpaceshipScanner.validateShip(shipData);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}