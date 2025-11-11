package com.zaphaos.runolith.equipment.mod_weapons;

import java.util.function.Supplier;

import com.zaphaos.runolith.CustTier;
import com.zaphaos.runolith.Runolith;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModWeapons {
	public static final DeferredRegister.Items WEAPONS = DeferredRegister.createItems(Runolith.MOD_ID);
	public static final Supplier<Item> EMERALD_SWORD = WEAPONS.register("emerald_sword", () -> new SwordItem(
            CustTier.EMERALD_TIER,
            new Item.Properties().attributes(
            		SwordItem.createAttributes(CustTier.EMERALD_TIER, 3, -2.2f))
    ));
	public static void register(IEventBus modEventBus) 
    {
		WEAPONS.register(modEventBus);
    }
}
