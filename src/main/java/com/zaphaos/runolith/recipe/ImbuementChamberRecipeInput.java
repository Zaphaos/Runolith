package com.zaphaos.runolith.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ImbuementChamberRecipeInput(ItemStack input) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return input;
	}

	@Override
	public int size() {
		return 1;
	}

}
