package com.zaphaos.runolith.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record ImbuementChamberRecipe(
		Ingredient ingredient,
		int tier,
		ItemStack output
		) implements Recipe<ImbuementChamberRecipeInput> {

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(ingredient);
		return list;
	}
	
	@Override
	public boolean matches(ImbuementChamberRecipeInput input, Level level) {
		if (level.isClientSide()) {
			return false;
		}
		return ingredient.test(input.getItem(0));
	}

	@Override
	public ItemStack assemble(ImbuementChamberRecipeInput input, Provider registries) {
		return output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
	    	return true;
	}

	@Override
	public ItemStack getResultItem(Provider registries) {
		return output;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
	    return ModRecipes.IMBUEMENT_CHAMBER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.IMBUEMENT_CHAMBER_TYPE.get();
	}
	
	public static class Serializer implements RecipeSerializer<ImbuementChamberRecipe>{
		public static final MapCodec<ImbuementChamberRecipe> CODEC =
				RecordCodecBuilder.mapCodec(inst -> inst.group(
					Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
                        .forGetter(ImbuementChamberRecipe::ingredient),
                    Codec.INT.fieldOf("tier")
                    	.forGetter(ImbuementChamberRecipe::tier),
                    ItemStack.CODEC.fieldOf("result")
                        .forGetter(ImbuementChamberRecipe::output)
                ).apply(inst, (ingredient, tier, primary) ->
                new ImbuementChamberRecipe(
                		ingredient, 
                		tier,
                		primary)));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ImbuementChamberRecipe> STREAM_CODEC =
	            StreamCodec.of(
	                (buf, recipe) -> {
	                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient());
	                    buf.writeInt(recipe.tier());
	                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
	                },
	                buf -> {
	                    Ingredient ing = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
	                    int tier = buf.readInt();
	                    ItemStack primary = ItemStack.STREAM_CODEC.decode(buf);
	                    return new ImbuementChamberRecipe(ing, tier, primary);
	                }
	            );
		
		@Override
		public MapCodec<ImbuementChamberRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ImbuementChamberRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
