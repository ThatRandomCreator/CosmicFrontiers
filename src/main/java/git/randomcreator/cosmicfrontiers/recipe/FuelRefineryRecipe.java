package git.randomcreator.cosmicfrontiers.recipe;

import com.google.gson.JsonObject;
import git.randomcreator.cosmicfrontiers.cosmicfrontiers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Recipe for the Fuel Refinery
 */
public class FuelRefineryRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack output;
    private final int processingTime;

    public FuelRefineryRecipe(ResourceLocation id, ItemStack input1, ItemStack input2,
                              ItemStack output, int processingTime) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.processingTime = processingTime;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (level.isClientSide()) return false;

        ItemStack slot0 = container.getItem(0);
        ItemStack slot1 = container.getItem(1);

        // Check if items match (order doesn't matter)
        return (slot0.getItem() == input1.getItem() && slot1.getItem() == input2.getItem()) ||
                (slot0.getItem() == input2.getItem() && slot1.getItem() == input1.getItem());
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    public ItemStack getInput1() {
        return input1;
    }

    public ItemStack getInput2() {
        return input2;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FuelRefineryRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fuel_refining";
    }

    public static class Serializer implements RecipeSerializer<FuelRefineryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(cosmicfrontiers.MODID, "fuel_refining");

        @Override
        public FuelRefineryRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack input1 = ItemStack.of(json.getAsJsonObject("input1"));
            ItemStack input2 = ItemStack.of(json.getAsJsonObject("input2"));
            ItemStack output = ItemStack.of(json.getAsJsonObject("output"));
            int processingTime = json.get("processingTime").getAsInt();

            return new FuelRefineryRecipe(recipeId, input1, input2, output, processingTime);
        }

        @Override
        public FuelRefineryRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack input1 = buffer.readItem();
            ItemStack input2 = buffer.readItem();
            ItemStack output = buffer.readItem();
            int processingTime = buffer.readInt();

            return new FuelRefineryRecipe(recipeId, input1, input2, output, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FuelRefineryRecipe recipe) {
            buffer.writeItem(recipe.input1);
            buffer.writeItem(recipe.input2);
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.processingTime);
        }
    }
}