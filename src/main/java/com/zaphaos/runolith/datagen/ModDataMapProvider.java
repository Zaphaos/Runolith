package com.zaphaos.runolith.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class ModDataMapProvider extends DataMapProvider {

	protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}
	@Override
	protected void gather() {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS)
			/*.add(ModItems.RUBY.getId(), new FurnaceFuel([burntime]), false)*/;
		 
		 
	}
}
