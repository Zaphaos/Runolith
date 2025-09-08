package com.zaphaos.runolith.compat;

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

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;

public class EnrichmentChamberRecipeCategory implements IRecipeCategory<EnrichmentChamberRecipe> {
	public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Runolith.MODID, "enrichment_chamber");
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Runolith.MODID, 
			"textures/gui/enrichment_chamber/enrichment_chamber_gui.png");
	
	public static final RecipeType<EnrichmentChamberRecipe> ENRICHMENT_CHAMBER_RECIPE_RECIPE_TYPE = 
			new RecipeType<>(UID, EnrichmentChamberRecipe.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	
	public EnrichmentChamberRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 40, 20, 116, 45); //+40,+20,-60,-40 //original: 0,0,176,85
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ENRICHMENT_CHAMBER));
	}
	
	@Override
	public RecipeType<EnrichmentChamberRecipe> getRecipeType() {
		return ENRICHMENT_CHAMBER_RECIPE_RECIPE_TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("block.runolith.enrichment_chamber");
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
	public void setRecipe(IRecipeLayoutBuilder builder, EnrichmentChamberRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 14, 14).addIngredients(recipe.getIngredients().get(0)); //original:  54,34
		
		builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 14).addItemStack(recipe.getResultItem(null));     //original: 104,34
		
		// Secondary Output (if exists)
	    if (!recipe.secondaryOutput().isEmpty()) {
	        builder.addSlot(RecipeIngredientRole.OUTPUT, 87, 18) // coords above main output
	               .addItemStack(recipe.secondaryOutput())
	               .addTooltipCallback((slotView, tooltip) -> {
	                   tooltip.add(Component.literal(
	                       String.format("%.0f%% chance", recipe.secondaryChance() * 100)
	                   ));
	               });
	    }
	}
	@Override
	public void draw(EnrichmentChamberRecipe recipe, IRecipeSlotsView recipeSlotsView,
	                 GuiGraphics graphics, double mouseX, double mouseY) {
		float chance = recipe.secondaryChance();

		if (chance > 0f && chance < 1f && !recipe.secondaryOutput().isEmpty()) {
		    // Multiply by 100 and round up
		    int percent = (int) Math.round(chance * 100f);

		    // Format as "0x%" if less than 10
		    String text = String.format("%02d%%", percent);

		    Minecraft mc = Minecraft.getInstance();
		    Font font = mc.font;

		    int slotX = 87;
		    int slotY = 18;
		    int textX = slotX;
		    int textY = slotY - 10;

		    graphics.drawString(font, text, textX, textY, 0xFF808080, false);
		}
	}


}
