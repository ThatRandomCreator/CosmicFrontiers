package git.randomcreator.cosmicfrontiers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Base class for all spaceship component blocks
 */
public abstract class SpaceshipComponentBlock extends Block {
    private final float weight;
    private final ComponentType componentType;

    public SpaceshipComponentBlock(BlockBehaviour.Properties properties, float weight, ComponentType componentType) {
        super(properties);
        this.weight = weight;
        this.componentType = componentType;
    }

    public float getWeight() {
        return weight;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public enum ComponentType {
        FUEL_TANK,
        ENGINE,
        CONTROLLER,
        SEAT,
        HULL,
        HULL_GLASS,
        AIR_LOCK,
        STRUCTURAL
    }
}