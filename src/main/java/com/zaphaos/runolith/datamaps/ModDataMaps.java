package com.zaphaos.runolith.datamaps;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.datamaps.imbuement.ImbuementFuel;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Runolith.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataMaps {
	public static final DataMapType<Item, ImbuementFuel> IMBUEMENT_FUEL = DataMapType.builder(
			id("imbuement_fuel"), Registries.ITEM, ImbuementFuel.CODEC).synced(ImbuementFuel.CODEC, false).build();
	
	private static ResourceLocation id(final String name) {
        return ResourceLocation.fromNamespaceAndPath(Runolith.MODID, name);
    }
	
	@SubscribeEvent
    private static void register(final RegisterDataMapTypesEvent event) {
		event.register(IMBUEMENT_FUEL);
	}
}
