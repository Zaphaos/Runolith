package com.zaphaos.runolith.block.entity;

import java.util.function.Supplier;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.block.ModBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
			DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Runolith.MODID);
	
	public static final Supplier<BlockEntityType<EnrichmentChamberBlockEntity>> ENRICHMENT_CHAMBER_BE =
			BLOCK_ENTITIES.register("enrichment_chamber_be", () -> BlockEntityType.Builder.of(
					EnrichmentChamberBlockEntity::new, ModBlocks.ENRICHMENT_CHAMBER.get()).build(null));
	public static final Supplier<BlockEntityType<GrowthChamberBlockEntity>> GROWTH_CHAMBER_BE =
			BLOCK_ENTITIES.register("growth_chamber_be", () -> BlockEntityType.Builder.of(
					GrowthChamberBlockEntity::new, ModBlocks.GROWTH_CHAMBER.get()).build(null));
	
	public static void register(IEventBus eventBus)  {
		BLOCK_ENTITIES.register(eventBus);
	}
}
