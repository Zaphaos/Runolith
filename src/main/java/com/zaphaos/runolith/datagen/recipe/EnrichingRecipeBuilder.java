package com.zaphaos.runolith.datagen.recipe;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;

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

public class EnrichingRecipeBuilder implements RecipeBuilder {
	private final Ingredient ingredient;
    private final Item primaryResult;
    private final int primaryCount;
    @Nullable
    private final Item secondaryResult;
    private int secondaryCount;
    private final float secondaryChance;
    private final RecipeCategory category;
    private final String group;
    
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    
    public EnrichingRecipeBuilder(Ingredient ingredient, ItemLike primaryResult, int count,
            @Nullable ItemLike secondaryResult, int secondaryCount, float secondaryChance,
            RecipeCategory category, String group) {
    	this.ingredient = ingredient;
    	this.primaryResult = primaryResult.asItem();
    	this.primaryCount = count;
    	this.secondaryResult = secondaryResult == null ? null : secondaryResult.asItem();
    	this.secondaryCount = secondaryCount;
    	this.secondaryChance = secondaryChance;
    	this.category = category;
    	this.group = group;
    }
    
    public static EnrichingRecipeBuilder enriching(ItemLike ingredient,
            RecipeCategory category, ItemLike primary, int count,
            @Nullable ItemLike secondary, int sCount, float chance,
            String group) {
    	return new EnrichingRecipeBuilder(Ingredient.of(ingredient), primary, count, secondary, sCount, chance, category, group);
    }

	@Override
	public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.criteria.put(name, criterion);
        return this;
	}

	@Override
	public RecipeBuilder group(String groupName) {
        return new EnrichingRecipeBuilder(this.ingredient, this.primaryResult, this.primaryCount,
                this.secondaryResult, this.secondaryCount, this.secondaryChance, this.category, groupName);
    }

	@Override
	public Item getResult() {
		return this.primaryResult;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		ensureValid(id);

	    // Build advancement (unlock on crafting)
	    Advancement.Builder advancementBuilder = Advancement.Builder.recipeAdvancement()
	            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
	            .rewards(AdvancementRewards.Builder.recipe(id))
	            .requirements(AdvancementRequirements.Strategy.OR);

	    this.criteria.forEach(advancementBuilder::addCriterion);

	    // Standard advancement id under recipes/<category>
	    ResourceLocation advancementId = id.withPrefix("recipes/" + this.category.getFolderName() + "/");

	    // Construct the recipe instance
	    EnrichmentChamberRecipe recipe = new EnrichmentChamberRecipe(
	            this.ingredient,
	            new ItemStack(this.primaryResult, this.primaryCount),
	            this.secondaryResult == null ? ItemStack.EMPTY : new ItemStack(this.secondaryResult, this.secondaryCount),
	            this.secondaryChance
	    );

	    // Hand recipe + advancement to datagen
	    recipeOutput.accept(id, recipe, advancementBuilder.build(advancementId));
    }

	private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
