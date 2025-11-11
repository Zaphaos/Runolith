package com.zaphaos.runolith.recipe;

import com.zaphaos.runolith.Runolith;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
			DeferredRegister.create(Registries.RECIPE_SERIALIZER, Runolith.MOD_ID);
	public static final DeferredRegister<RecipeType<?>> TYPES =
			DeferredRegister.create(Registries.RECIPE_TYPE, Runolith.MOD_ID);
	
	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EnrichmentChamberRecipe>> ENRICHMENT_CHAMBER_SERIALIZER =
			SERIALIZERS.register("enrichment_chamber", EnrichmentChamberRecipe.Serializer::new);
	public static final DeferredHolder<RecipeType<?>, RecipeType<EnrichmentChamberRecipe>> ENRICHMENT_CHAMBER_TYPE =
			TYPES.register("enrichment_chamber", () -> new RecipeType<EnrichmentChamberRecipe>() {
				@Override
				public String toString() {
					return "enrichment_chamber";
				}
			});
	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ImbuementChamberRecipe>> IMBUEMENT_CHAMBER_SERIALIZER =
			SERIALIZERS.register("imbuement_chamber", ImbuementChamberRecipe.Serializer::new);
	public static final DeferredHolder<RecipeType<?>, RecipeType<ImbuementChamberRecipe>> IMBUEMENT_CHAMBER_TYPE =
			TYPES.register("imbuement_chamber", () -> new RecipeType<ImbuementChamberRecipe>() {
				@Override
				public String toString() {
					return "imbuement_chamber";
				}
			});
	
	public static void register(IEventBus eventBus) {
		SERIALIZERS.register(eventBus);
		TYPES.register(eventBus);
	}
}
