package com.zaphaos.runolith.block;

import java.util.function.Supplier;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.custom.EnrichmentChamberBlock;
import com.zaphaos.runolith.block.custom.GrowthChamberBlock;
import com.zaphaos.runolith.block.custom.ImbuementChamberBlock;
import com.zaphaos.runolith.item.ModItems;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS =
			DeferredRegister.createBlocks(Runolith.MODID);
	
	public static final DeferredBlock<Block> RUBY_BLOCK = registerBlock("ruby_block",
			() -> new Block(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_RED)
					.instrument(NoteBlockInstrument.BIT)
					.requiresCorrectToolForDrops()
					.strength(5.0f, 6.0f)
					.sound(SoundType.METAL)));
	
	public static final DeferredBlock<Block> IMPURE_RUBY_BLOCK = registerBlock("impure_ruby_block",
			() -> new Block(BlockBehaviour.Properties.of()
					.mapColor(MapColor.QUARTZ)
					.instrument(NoteBlockInstrument.BASEDRUM)
					.requiresCorrectToolForDrops()
					.strength(5.0f, 6.0f)));
	
	public static final DeferredBlock<Block> RUBY_ORE = registerBlock("ruby_ore", 
			() -> new DropExperienceBlock(UniformInt.of(3, 7),
					BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	
	public static final DeferredBlock<Block> DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
	        () -> new DropExperienceBlock(
	            UniformInt.of(3, 7),
	            BlockBehaviour.Properties.ofFullCopy(RUBY_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
	
	public static final DeferredBlock<Block> ENRICHMENT_CHAMBER = registerBlock("enrichment_chamber",
			() -> new EnrichmentChamberBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_LIGHT_GRAY)
					.requiresCorrectToolForDrops()
					.strength(2.0f, 3.0f)
					.sound(SoundType.METAL)));
	public static final DeferredBlock<Block> GROWTH_CHAMBER = registerBlock("growth_chamber",
			() -> new GrowthChamberBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_LIGHT_GRAY)
					.requiresCorrectToolForDrops()
					.strength(2.0f, 3.0f)
					.sound(SoundType.METAL)));
	public static final DeferredBlock<Block> IMBUEMENT_CHAMBER = registerBlock("imbuement_chamber",
			() -> new ImbuementChamberBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_LIGHT_GRAY)
					.requiresCorrectToolForDrops()
					.strength(2.0f, 3.0f)
					.sound(SoundType.METAL)));
	
	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
		DeferredBlock<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}
	
	private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)  {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}
	
	public static void register(IEventBus eventbus) {
		BLOCKS.register(eventbus);
	}
}
