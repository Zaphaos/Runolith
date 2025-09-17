package com.zaphaos.runolith.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.equipment.mod_armor.ModArmor;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;
import com.zaphaos.runolith.equipment.mod_weapons.ModWeapons;
import com.zaphaos.runolith.item.ModItems;
import com.zaphaos.runolith.util.ModTags;
import com.zaphaos.runolith.datagen.recipe.EnrichingRecipeBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}
	
	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ENRICHMENT_CHAMBER.get())
			.pattern("RAR")
			.pattern("OIO")
			.pattern("OGO")
			.define('R', ModTags.Items.GEMS)
			.define('A', Items.AMETHYST_SHARD)
			.define('O', Items.OBSIDIAN)
			.define('I', Items.IRON_BLOCK)
			.define('G', Items.GLOWSTONE_DUST)
			.unlockedBy("has_gem_item", has(ModTags.Items.GEMS)).save(recipeOutput);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get())
			.pattern("RRR")
			.pattern("RRR")
			.pattern("RRR")
			.define('R', ModItems.RUBY.get())
			.unlockedBy("has_ruby", has(ModItems.RUBY)).save(recipeOutput);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY, 9)
			.requires(ModBlocks.RUBY_BLOCK)
			.unlockedBy("has_ruby_block", has(ModBlocks.RUBY_BLOCK)).save(recipeOutput);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.IMPURE_RUBY_BLOCK.get())
			.pattern("RRR")
			.pattern("RRR")
			.pattern("RRR")
			.define('R', ModItems.IMPURE_RUBY.get())
			.unlockedBy("has_impure_ruby", has(ModItems.IMPURE_RUBY)).save(recipeOutput);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IMPURE_RUBY, 9)
			.requires(ModBlocks.IMPURE_RUBY_BLOCK)
			.unlockedBy("has_impure_ruby_block", has(ModBlocks.IMPURE_RUBY_BLOCK)).save(recipeOutput);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmor.EMERALD_HELMET.get())
			.pattern("EEE")
			.pattern("E E")
			.define('E', Items.EMERALD)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmor.EMERALD_CHESTPLATE.get())
			.pattern("E E")
			.pattern("EEE")
			.pattern("EEE")
			.define('E', Items.EMERALD)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmor.EMERALD_LEGGINGS.get())
			.pattern("EEE")
			.pattern("E E")
			.pattern("E E")
			.define('E', Items.EMERALD)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmor.EMERALD_BOOTS.get())
			.pattern("E E")
			.pattern("E E")
			.define('E', Items.EMERALD)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.EMERALD_SWORD.get())
			.pattern("E")
			.pattern("E")
			.pattern("S")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_PICKAXE.get())
			.pattern("EEE")
			.pattern(" S ")
			.pattern(" S ")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_AXE.get())
			.pattern("EE")
			.pattern("ES")
			.pattern(" S")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_AXE.get())
			.pattern("EE")
			.pattern("SE")
			.pattern("S ")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput, "runolith:emerald_axe_mirrored");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_SHOVEL.get())
			.pattern("E")
			.pattern("S")
			.pattern("S")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_HOE.get())
			.pattern("EE")
			.pattern(" S")
			.pattern(" S")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModTools.EMERALD_HOE.get())
			.pattern("EE")
			.pattern("S ")
			.pattern("S ")
			.define('E', Items.EMERALD)
			.define('S', Items.STICK)
			.unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput, "runolith:emerald_hoe_mirrored");
		
		
		List<ItemLike> RUBY_SMELTABLES = List.of(
				ModItems.GEM_NODE_RUBY, ModItems.IMPURE_RUBY,
				ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.RUBY_ORE);
		oreSmelting(recipeOutput, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.25f, 200, "ruby");
		oreBlasting(recipeOutput, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.25f, 100, "ruby");
		
		List<ItemLike> DIAMOND_SMELTABLES = List.of(
				ModItems.GEM_NODE_DIAMOND);
		oreSmelting(recipeOutput, DIAMOND_SMELTABLES, RecipeCategory.MISC, Items.DIAMOND, 0.25f, 200, "diamond");
		oreBlasting(recipeOutput, DIAMOND_SMELTABLES, RecipeCategory.MISC, Items.DIAMOND, 0.25f, 100, "diamond");
		
		List<ItemLike> EMERALD_SMELTABLES = List.of(
				ModItems.GEM_NODE_EMERALD);
		oreSmelting(recipeOutput, EMERALD_SMELTABLES, RecipeCategory.MISC, Items.EMERALD, 0.25f, 200, "emerald");
		oreBlasting(recipeOutput, EMERALD_SMELTABLES, RecipeCategory.MISC, Items.EMERALD, 0.25f, 100, "emerald");
		
		enriching(recipeOutput, ModBlocks.RUBY_ORE, 0.1f, RecipeCategory.MISC, ModItems.RUBY, 2, ModItems.GEM_NODE_RUBY, 1, "ruby");
		enriching(recipeOutput, ModBlocks.DEEPSLATE_RUBY_ORE, 0.15f, RecipeCategory.MISC, ModItems.RUBY, 2, ModItems.GEM_NODE_RUBY, 1, "ruby");
		enriching(recipeOutput, ModItems.IMPURE_RUBY, 0.75f, RecipeCategory.MISC, ModItems.RUBY, 1, ModItems.RUBY, 1, "ruby");
		enriching(recipeOutput, ModItems.GEM_NODE_RUBY, 0.5f, RecipeCategory.MISC, ModItems.RUBY, 1, ModItems.RUBY, 1, "ruby");
		
		enriching(recipeOutput, Items.DIAMOND_ORE, 0.05f, RecipeCategory.MISC, Items.DIAMOND, 2, ModItems.GEM_NODE_DIAMOND, 1, "diamond");
		enriching(recipeOutput, Items.DEEPSLATE_DIAMOND_ORE, 0.075f, RecipeCategory.MISC, Items.DIAMOND, 2, ModItems.GEM_NODE_DIAMOND, 1, "diamond");
		enriching(recipeOutput, ModItems.GEM_NODE_DIAMOND, 0.33f, RecipeCategory.MISC, Items.DIAMOND, 1, Items.DIAMOND, 1, "diamond");
		
		enriching(recipeOutput, Items.EMERALD_ORE, 0.1f, RecipeCategory.MISC, Items.EMERALD, 2, ModItems.GEM_NODE_EMERALD, 1, "emerald");
		enriching(recipeOutput, Items.DEEPSLATE_EMERALD_ORE, 0.15f, RecipeCategory.MISC, Items.EMERALD, 2, ModItems.GEM_NODE_EMERALD, 1, "emerald");
		enriching(recipeOutput, ModItems.GEM_NODE_EMERALD, 0.5f, RecipeCategory.MISC, Items.EMERALD, 1, Items.EMERALD, 1, "emerald");
	}
	protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
		float pExperience, int pCookingTIme, String pGroup) {
		oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
		pExperience, pCookingTIme, pGroup, "_from_smelting");
	}

	protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
		float pExperience, int pCookingTime, String pGroup) {
		oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
		pExperience, pCookingTime, pGroup, "_from_blasting");
	}

	protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                 List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
		for(ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
				.save(recipeOutput, Runolith.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}
	
	protected static void enriching(RecipeOutput recipeOutput, ItemLike ingredient, float chance,
            						RecipeCategory category, ItemLike primary, int pCount, ItemLike secondary, int sCount, String group) {
		EnrichingRecipeBuilder.enriching(ingredient, category, primary, pCount, secondary, sCount, chance, group)
		.unlockedBy("has_" + ingredient.asItem().toString(), net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
		.save(recipeOutput, Runolith.MODID + ":" + getItemName(primary) + "_from_enriching_" + getItemName(ingredient));
	}
}
