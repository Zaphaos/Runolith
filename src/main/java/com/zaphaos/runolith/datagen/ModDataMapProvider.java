package com.zaphaos.runolith.datagen;

import java.util.concurrent.CompletableFuture;

import com.zaphaos.runolith.datamaps.ModDataMaps;
import com.zaphaos.runolith.datamaps.imbuement.ImbuementFuel;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class ModDataMapProvider extends DataMapProvider {

	protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}
	@Override
	protected void gather() {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS);
		this.builder(ModDataMaps.IMBUEMENT_FUEL)
			.add(id(Items.GLOWSTONE_DUST), new ImbuementFuel(1, 1), false);
		
	}
	
	private ResourceLocation id(ItemLike itemLike) {
		return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
	}
	
}
