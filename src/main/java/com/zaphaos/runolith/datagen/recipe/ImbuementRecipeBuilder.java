package com.zaphaos.runolith.datagen.recipe;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;
import com.zaphaos.runolith.recipe.ImbuementChamberRecipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ImbuementRecipeBuilder implements RecipeBuilder {
	private final Ingredient ingredient;
    private final Item result;
    private final int count;
    private final RecipeCategory category;
    private final String group;
    
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	
    public ImbuementRecipeBuilder(Ingredient ingredient, ItemLike result, int count, RecipeCategory category, String group) {
    	this.ingredient = ingredient;
    	this.result = result.asItem();
    	this.count = count;
    	this.category = category;
    	this.group = group;
    }
    
    public static ImbuementRecipeBuilder imbuing(ItemLike ingredient, RecipeCategory category, ItemLike primary, int count, String group) {
    	return new ImbuementRecipeBuilder(Ingredient.of(ingredient), primary, count, category, group);
    }
    
	@Override
	public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.criteria.put(name, criterion);
        return this;
	}

	@Override
	public RecipeBuilder group(String groupName) {
		return new ImbuementRecipeBuilder(this.ingredient, this.result, this.count, this.category, groupName);
	}

	@Override
	public Item getResult() {
		return this.result;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
	    ensureValid(id);

	    Advancement.Builder advancementBuilder = Advancement.Builder.recipeAdvancement()
	            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
	            .rewards(AdvancementRewards.Builder.recipe(id))
	            .requirements(AdvancementRequirements.Strategy.OR);

	    this.criteria.forEach(advancementBuilder::addCriterion);

	    ResourceLocation advancementId = id.withPrefix("recipes/" + this.category.getFolderName() + "/");

	    // Construct the actual recipe
	    ImbuementChamberRecipe recipe = new ImbuementChamberRecipe(
	            this.ingredient,
	            new ItemStack(this.result, this.count)
	    );

	    recipeOutput.accept(id, recipe, advancementBuilder.build(advancementId));
	}
	
	private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
