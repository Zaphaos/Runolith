package com.zaphaos.runolith.compat;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;
import com.zaphaos.runolith.recipe.ImbuementChamberRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ImbuementChamberRecipeCategory implements IRecipeCategory<ImbuementChamberRecipe> {
	public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Runolith.MODID, "imbuement_chamber");
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Runolith.MODID, 
			"textures/gui/imbuement_chamber/imbuement_chamber_gui.png");
	
	public static final RecipeType<ImbuementChamberRecipe> IMBUEMENT_CHAMBER_RECIPE_RECIPE_TYPE = 
			new RecipeType<>(UID, ImbuementChamberRecipe.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public ImbuementChamberRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 40, 20, 116, 45); //+40,+20,-60,-40 //original: 0,0,176,85
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.IMBUEMENT_CHAMBER));
	}
	
	@Override
	public RecipeType<ImbuementChamberRecipe> getRecipeType() {
		return IMBUEMENT_CHAMBER_RECIPE_RECIPE_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("block.runolith.imbuement_chamber");
	}

	@Override
	public @Nullable IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ImbuementChamberRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 14, 14).addIngredients(recipe.getIngredients().get(0)); //original:  54,34
		builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 14).addItemStack(recipe.getResultItem(null));     //original: 104,34
	}
}
