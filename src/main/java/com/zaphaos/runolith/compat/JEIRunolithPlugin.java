package com.zaphaos.runolith.compat;

import java.util.List;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;
import com.zaphaos.runolith.recipe.ImbuementChamberRecipe;
import com.zaphaos.runolith.recipe.ModRecipes;
import com.zaphaos.runolith.screen.custom.EnrichmentChamberScreen;
import com.zaphaos.runolith.screen.custom.ImbuementChamberScreen;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIRunolithPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return ResourceLocation.fromNamespaceAndPath(Runolith.MODID, "jei_plugin");
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new EnrichmentChamberRecipeCategory(
				registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new ImbuementChamberRecipeCategory(
				registration.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		
		List<EnrichmentChamberRecipe> enrichmentChamberRecipe = recipeManager
				.getAllRecipesFor(ModRecipes.ENRICHMENT_CHAMBER_TYPE.get()).stream().map(RecipeHolder::value).toList();
		registration.addRecipes(EnrichmentChamberRecipeCategory.ENRICHMENT_CHAMBER_RECIPE_RECIPE_TYPE, enrichmentChamberRecipe);
		List<ImbuementChamberRecipe> imbuementChamberRecipe = recipeManager
				.getAllRecipesFor(ModRecipes.IMBUEMENT_CHAMBER_TYPE.get()).stream().map(RecipeHolder::value).toList();
		registration.addRecipes(ImbuementChamberRecipeCategory.IMBUEMENT_CHAMBER_RECIPE_RECIPE_TYPE, imbuementChamberRecipe);
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(EnrichmentChamberScreen.class, 74, 30, 22, 20,
				EnrichmentChamberRecipeCategory.ENRICHMENT_CHAMBER_RECIPE_RECIPE_TYPE);
		registration.addRecipeClickArea(ImbuementChamberScreen.class, 30, 30, 20, 20,
				ImbuementChamberRecipeCategory.IMBUEMENT_CHAMBER_RECIPE_RECIPE_TYPE);
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.ENRICHMENT_CHAMBER.asItem()), 
				EnrichmentChamberRecipeCategory.ENRICHMENT_CHAMBER_RECIPE_RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.IMBUEMENT_CHAMBER.asItem()),
				ImbuementChamberRecipeCategory.IMBUEMENT_CHAMBER_RECIPE_RECIPE_TYPE);
	}
}
