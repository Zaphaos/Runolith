package com.zaphaos.runolith.datagen;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Runolith.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		blockWithItem(ModBlocks.DEEPSLATE_RUBY_ORE);
		blockWithItem(ModBlocks.ENRICHMENT_CHAMBER);
		//blockWithItem(ModBlocks.GROWTH_CHAMBER);
		blockWithItem(ModBlocks.IMPURE_RUBY_BLOCK);
		blockWithItem(ModBlocks.RUBY_BLOCK);
		blockWithItem(ModBlocks.RUBY_ORE);
	}
	
	private void blockWithItem(DeferredBlock<?> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
	}
}
