package git.randomcreator.cosmicfrontiers.world.dimension;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

/**
 * Space dimension - a void world with floating planets
 */
public class ModDimensions {
    public static final ResourceKey<Level> SPACE_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(cosmicfrontiers.MODID, "space"));

    public static final ResourceKey<DimensionType> SPACE_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(cosmicfrontiers.MODID, "space"));

    // Planet dimensions that can be traveled to from space
    public static final ResourceKey<Level> MARS_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(cosmicfrontiers.MODID, "mars"));

    public static final ResourceKey<Level> MOON_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(cosmicfrontiers.MODID, "moon"));

    public static final ResourceKey<Level> VENUS_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(cosmicfrontiers.MODID, "venus"));

    public static final ResourceKey<Level> EUROPA_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(cosmicfrontiers.MODID, "europa"));

    public static void register() {
        // Dimensions are registered via JSON files
        // This class just holds the keys for reference
    }
}