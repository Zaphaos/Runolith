package com.zaphaos.runolith.datagen;

import java.util.Set;

import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.item.ModItems;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

	protected ModBlockLootTableProvider(Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		dropSelf(ModBlocks.RUBY_BLOCK.get());
		dropSelf(ModBlocks.IMPURE_RUBY_BLOCK.get());
		dropSelf(ModBlocks.ENRICHMENT_CHAMBER.get());
		dropSelf(ModBlocks.GROWTH_CHAMBER.get());
		dropSelf(ModBlocks.IMBUEMENT_CHAMBER.get());
		dropSelf(ModBlocks.DYNAMIC_ITEM_PEDESTAL.get());
		
		add(ModBlocks.RUBY_ORE.get(),
				block -> createOreDrop(ModBlocks.RUBY_ORE.get(), ModItems.IMPURE_RUBY.get()));
		add(ModBlocks.DEEPSLATE_RUBY_ORE.get(),
				block -> createOreDrop(ModBlocks.RUBY_ORE.get(), ModItems.IMPURE_RUBY.get()));
	}
	
	protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
		HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createSilkTouchDispatchTable(pBlock,
				this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
						.apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
