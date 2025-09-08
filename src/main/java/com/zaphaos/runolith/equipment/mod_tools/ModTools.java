package com.zaphaos.runolith.equipment.mod_tools;

import java.util.function.Supplier;


import com.zaphaos.runolith.CustTier;
import com.zaphaos.runolith.Runolith;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;

public class ModTools {
	public static final DeferredRegister.Items TOOLS = DeferredRegister.createItems(Runolith.MODID);
    public static final Supplier<PickaxeItem> EMERALD_PICKAXE = TOOLS.register("emerald_pickaxe", () ->
    new PickaxeItem(
        CustTier.EMERALD_TIER,
        new Item.Properties().attributes(
        		PickaxeItem.createAttributes(CustTier.EMERALD_TIER, 1, -2.8f))
    ));

    public static final Supplier<AxeItem> EMERALD_AXE = TOOLS.register("emerald_axe", () ->
    new AxeItem(
        CustTier.EMERALD_TIER,
        new Item.Properties().attributes(
        		AxeItem.createAttributes(CustTier.EMERALD_TIER, 5, -2.8f))
    ));

	public static final Supplier<ShovelItem> EMERALD_SHOVEL = TOOLS.register("emerald_shovel", () ->
    new ShovelItem(
        CustTier.EMERALD_TIER,
        new Item.Properties().attributes(
        		ShovelItem.createAttributes(CustTier.EMERALD_TIER, 1.5f, -3.0f))
    ));

	public static final Supplier<HoeItem> EMERALD_HOE = TOOLS.register("emerald_hoe", () ->
    new HoeItem(
        CustTier.EMERALD_TIER,
        new Item.Properties().attributes(
        		AxeItem.createAttributes(CustTier.EMERALD_TIER, -1.0f, -1.0f))
    ));
    public static void register(IEventBus modEventBus) 
    {
        TOOLS.register(modEventBus);
    }
}
