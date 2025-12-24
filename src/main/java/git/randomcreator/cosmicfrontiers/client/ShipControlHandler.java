package git.randomcreator.cosmicfrontiers.client;

import git.randomcreator.cosmicfrontiers.entity.SpaceshipEntity;
import git.randomcreator.cosmicfrontiers.network.ShipControlPacket;
import git.randomcreator.cosmicfrontiers.world.structure.Planet;
import git.randomcreator.cosmicfrontiers.world.structure.PlanetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * Handles player input for controlling spaceships
 */
@Mod.EventBusSubscriber
public class ShipControlHandler {
    private static boolean isControllingShip = false;
    private static SpaceshipEntity controlledShip = null;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Check if player is in/near a spaceship
        Entity vehicle = mc.player.getVehicle();
        if (vehicle instanceof SpaceshipEntity ship) {
            isControllingShip = true;
            controlledShip = ship;
        }

        if (!isControllingShip || controlledShip == null) return;

        // Ship controls
        int key = event.getKey();
        int action = event.getAction();

        if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
            switch (key) {
                case GLFW.GLFW_KEY_W -> {
                    // Increase throttle
                    sendControlPacket(ShipControlPacket.ControlType.THROTTLE_UP);
                }
                case GLFW.GLFW_KEY_S -> {
                    // Decrease throttle
                    sendControlPacket(ShipControlPacket.ControlType.THROTTLE_DOWN);
                }
                case GLFW.GLFW_KEY_A -> {
                    // Yaw left
                    sendControlPacket(ShipControlPacket.ControlType.YAW_LEFT);
                }
                case GLFW.GLFW_KEY_D -> {
                    // Yaw right
                    sendControlPacket(ShipControlPacket.ControlType.YAW_RIGHT);
                }
                case GLFW.GLFW_KEY_SPACE -> {
                    // Pitch up
                    sendControlPacket(ShipControlPacket.ControlType.PITCH_UP);
                }
                case GLFW.GLFW_KEY_LEFT_SHIFT -> {
                    // Pitch down
                    sendControlPacket(ShipControlPacket.ControlType.PITCH_DOWN);
                }
                case GLFW.GLFW_KEY_X -> {
                    // Stop engines
                    sendControlPacket(ShipControlPacket.ControlType.FULL_STOP);
                }
                case GLFW.GLFW_KEY_L -> {
                    // Attempt landing
                    Planet nearbyPlanet = PlanetManager.getPlanetForLanding(controlledShip.blockPosition());
                    if (nearbyPlanet != null) {
                        sendControlPacket(ShipControlPacket.ControlType.LAND);
                    }
                }
            }
        }
    }

    private static void sendControlPacket(ShipControlPacket.ControlType controlType) {
        // TODO: Send packet to server
        // PacketHandler.INSTANCE.sendToServer(new ShipControlPacket(controlledShip.getId(), controlType));
    }

    public static void stopControlling() {
        isControllingShip = false;
        controlledShip = null;
    }
}