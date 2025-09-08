package com.zaphaos.runolith.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record EnrichmentChamberRecipe(
		Ingredient inputItem, 
		ItemStack primaryOutput,
		@Nullable ItemStack secondaryOutput,
		float secondaryChance // 0.0f – 1.0f
		) implements Recipe<EnrichmentChamberRecipeInput> {
	//inputItem & output ==> Read From JSON file
	//EnrichmentChamberRecipeInput --> INVENTORY of the Block Entity
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(inputItem);
		return list;
	}
	
	@Override
	public boolean matches(EnrichmentChamberRecipeInput enrichmentChamberRecipeInput, Level level) {
		if (level.isClientSide()) {
			return false;
		}
		return inputItem.test(enrichmentChamberRecipeInput.getItem(0));
	}

	@Override
	public ItemStack assemble(EnrichmentChamberRecipeInput enrichmentChamberRecipeInput, Provider registries) {
		return primaryOutput.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(Provider registries) {
		return primaryOutput;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.ENRICHMENT_CHAMBER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.ENRICHMENT_CHAMBER_TYPE.get();
	}
	
	public List<ItemStack> getAllOutputs() {
	    List<ItemStack> results = new ArrayList<>();
	    results.add(primaryOutput.copy());
	    if (secondaryOutput != null && !secondaryOutput.isEmpty()) {
	        results.add(secondaryOutput.copy());
	    }
	    return results;
	}
	
	public static class Serializer implements RecipeSerializer<EnrichmentChamberRecipe>{
		/*public static final MapCodec<EnrichmentChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(EnrichmentChamberRecipe::inputItem), 
				ItemStack.CODEC.fieldOf("result").forGetter(EnrichmentChamberRecipe::primaryOutput)
		).apply(inst, EnrichmentChamberRecipe::new));*/
		
		/*public static final StreamCodec<RegistryFriendlyByteBuf, EnrichmentChamberRecipe> STREAM_CODEC =
				StreamCodec.composite(
						Ingredient.CONTENTS_STREAM_CODEC, EnrichmentChamberRecipe::inputItem, 
						ItemStack.STREAM_CODEC, EnrichmentChamberRecipe::primaryOutput, 
						EnrichmentChamberRecipe::new);*/
	        public static final MapCodec<EnrichmentChamberRecipe> CODEC =
	            RecordCodecBuilder.mapCodec(inst -> inst.group(
	                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
	                        .forGetter(EnrichmentChamberRecipe::inputItem),
	                ItemStack.CODEC.fieldOf("primary_result")
	                        .forGetter(EnrichmentChamberRecipe::primaryOutput),
	                ItemStack.CODEC.optionalFieldOf("secondary_result")
	                        .forGetter(r -> r.secondaryOutput().isEmpty() ? Optional.empty() : Optional.of(r.secondaryOutput())),
	                Codec.FLOAT.optionalFieldOf("secondary_chance", 0.0f) // default 0%
	                        .forGetter(EnrichmentChamberRecipe::secondaryChance)
	            ).apply(inst, (ingredient, primary, secondaryOpt, chance) ->
	                new EnrichmentChamberRecipe(
	                		ingredient, 
	                		primary, 
	                		secondaryOpt.orElse(ItemStack.EMPTY), 
	                		chance)));
		/*public static final StreamCodec<RegistryFriendlyByteBuf, EnrichmentChamberRecipe> STREAM_CODEC =
	            StreamCodec.of(
	                (buf, recipe) -> {
	                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem());
	                    ItemStack.STREAM_CODEC.encode(buf, recipe.primaryOutput());
	                    ItemStack.STREAM_CODEC.encode(buf, recipe.secondaryOutput());
	                },
	                buf -> {
	                    Ingredient ing = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
	                    ItemStack primary = ItemStack.STREAM_CODEC.decode(buf);
	                    ItemStack secondary = ItemStack.STREAM_CODEC.decode(buf);
	                    return new EnrichmentChamberRecipe(ing, primary, secondary);
	                }
	            );*/
	        public static final StreamCodec<RegistryFriendlyByteBuf, EnrichmentChamberRecipe> STREAM_CODEC =
	        	    StreamCodec.of(
	        	        (buf, recipe) -> {
	        	            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem());
	        	            ItemStack.STREAM_CODEC.encode(buf, recipe.primaryOutput());

	        	            ByteBufCodecs.optional(ItemStack.STREAM_CODEC).encode(
	        	                buf,
	        	                recipe.secondaryOutput().isEmpty()
	        	                    ? Optional.empty()
	        	                    : Optional.of(recipe.secondaryOutput())
	        	            );

	        	            buf.writeFloat(recipe.secondaryChance()); // new
	        	        },
	        	        buf -> {
	        	            Ingredient ing = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
	        	            ItemStack primary = ItemStack.STREAM_CODEC.decode(buf);

	        	            Optional<ItemStack> secondaryOpt =
	        	                ByteBufCodecs.optional(ItemStack.STREAM_CODEC).decode(buf);

	        	            float chance = buf.readFloat(); // new

	        	            return new EnrichmentChamberRecipe(
	        	                ing,
	        	                primary,
	        	                secondaryOpt.orElse(ItemStack.EMPTY),
	        	                chance
	        	            );
	        	        }
	        	    );

		
		@Override
		public MapCodec<EnrichmentChamberRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EnrichmentChamberRecipe> streamCodec() {
			return STREAM_CODEC;
		}
		
	}
}
