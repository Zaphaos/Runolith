package com.zaphaos.runolith.datagen;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Runolith.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		blockWithItem(ModBlocks.DEEPSLATE_RUBY_ORE);
		blockWithItem(ModBlocks.ENRICHMENT_CHAMBER);
		blockWithItem(ModBlocks.IMPURE_RUBY_BLOCK);
		blockWithItem(ModBlocks.RUBY_BLOCK);
		blockWithItem(ModBlocks.RUBY_ORE);
		
		//blockWithItemCubeBottomTop(ModBlocks.GROWTH_CHAMBER, "growth_chamber_side", "growth_chamber_bottom", "growth_chamber_top");
		//blockWithItemCubeBottomTop(ModBlocks.IMBUEMENT_CHAMBER, "imbuement_chamber_side", "imbuement_chamber_bottom", "imbuement_chamber_top");
	}
	
	private void blockWithItem(DeferredBlock<?> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
	}

	private void blockWithItemCubeBottomTop(DeferredBlock<?> block, String side, String bottom, String top) {
		simpleBlockWithItem(block.get(), models().cubeBottomTop(
            block.getId().getPath(),   // model name
            modLoc("block/" + side),   // side texture
            modLoc("block/" + bottom), // bottom texture
            modLoc("block/" + top)     // top texture
			)
		);
	}

}
