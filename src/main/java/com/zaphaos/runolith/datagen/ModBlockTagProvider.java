package com.zaphaos.runolith.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockTagProvider extends BlockTagsProvider {

	public ModBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Runolith.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ModBlocks.RUBY_BLOCK.get())
			.add(ModBlocks.IMPURE_RUBY_BLOCK.get())
			.add(ModBlocks.RUBY_ORE.get())
			.add(ModBlocks.DEEPSLATE_RUBY_ORE.get())
			.add(ModBlocks.ENRICHMENT_CHAMBER.get())
			.add(ModBlocks.GROWTH_CHAMBER.get());
		
		tag(BlockTags.NEEDS_IRON_TOOL)
			.add(ModBlocks.RUBY_BLOCK.get())
			.add(ModBlocks.IMPURE_RUBY_BLOCK.get())
			.add(ModBlocks.RUBY_ORE.get())
			.add(ModBlocks.DEEPSLATE_RUBY_ORE.get());
	}
}
