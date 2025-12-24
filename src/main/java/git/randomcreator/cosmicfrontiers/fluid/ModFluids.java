package git.randomcreator.cosmicfrontiers.fluid;

import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL =
            new ResourceLocation("minecraft", "block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL =
            new ResourceLocation("minecraft", "block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL =
            new ResourceLocation("minecraft", "block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, cosmicfrontiers.MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, cosmicfrontiers.MODID);

    // Rocket Fuel
    public static final RegistryObject<FluidType> ROCKET_FUEL_FLUID_TYPE = FLUID_TYPES.register("rocket_fuel",
            () -> new FluidType(FluidType.Properties.create()
                    .lightLevel(2)
                    .density(800)
                    .viscosity(1500)
                    .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)) {
                @Override
                public String getDescriptionId() {
                    return "fluid.cosmicfrontiers.rocket_fuel";
                }
            });

    public static final RegistryObject<FlowingFluid> SOURCE_ROCKET_FUEL = FLUIDS.register("rocket_fuel",
            () -> new RocketFuelFluid.Source());

    public static final RegistryObject<FlowingFluid> FLOWING_ROCKET_FUEL = FLUIDS.register("flowing_rocket_fuel",
            () -> new RocketFuelFluid.Flowing());

    // Liquid Oxygen
    public static final RegistryObject<FluidType> LIQUID_OXYGEN_FLUID_TYPE = FLUID_TYPES.register("liquid_oxygen",
            () -> new FluidType(FluidType.Properties.create()
                    .lightLevel(0)
                    .density(1140)
                    .viscosity(500)
                    .temperature(90) // Very cold
                    .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)) {
                @Override
                public String getDescriptionId() {
                    return "fluid.cosmicfrontiers.liquid_oxygen";
                }
            });

    public static final RegistryObject<FlowingFluid> SOURCE_LIQUID_OXYGEN = FLUIDS.register("liquid_oxygen",
            () -> new LiquidOxygenFluid.Source());

    public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_OXYGEN = FLUIDS.register("flowing_liquid_oxygen",
            () -> new LiquidOxygenFluid.Flowing());

    // Liquid Hydrogen
    public static final RegistryObject<FluidType> LIQUID_HYDROGEN_FLUID_TYPE = FLUID_TYPES.register("liquid_hydrogen",
            () -> new FluidType(FluidType.Properties.create()
                    .lightLevel(0)
                    .density(70)
                    .viscosity(200)
                    .temperature(20) // Extremely cold
                    .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)) {
                @Override
                public String getDescriptionId() {
                    return "fluid.cosmicfrontiers.liquid_hydrogen";
                }
            });

    public static final RegistryObject<FlowingFluid> SOURCE_LIQUID_HYDROGEN = FLUIDS.register("liquid_hydrogen",
            () -> new LiquidHydrogenFluid.Source());

    public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_HYDROGEN = FLUIDS.register("flowing_liquid_hydrogen",
            () -> new LiquidHydrogenFluid.Flowing());
}