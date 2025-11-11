package com.zaphaos.runolith.equipment.mod_armor;

import com.zaphaos.runolith.CustTier;
import com.zaphaos.runolith.Runolith;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModArmor {
	public static final DeferredRegister.Items ARMOR = DeferredRegister.createItems(Runolith.MOD_ID);

    public static final DeferredItem<ArmorItem> EMERALD_HELMET =
    		ARMOR.register("emerald_helmet", () -> new ArmorItem(
    	        CustTier.EMERALD_ARMOR,            // Holder<ArmorMaterial>
    	        ArmorItem.Type.HELMET,             // new enum in 1.21.x
    	        new Item.Properties()
    	    ));
    
    public static final DeferredItem<ArmorItem> EMERALD_CHESTPLATE =
    		ARMOR.register("emerald_chestplate", () -> new ArmorItem(
    	        CustTier.EMERALD_ARMOR,            // Holder<ArmorMaterial>
    	        ArmorItem.Type.CHESTPLATE,             // new enum in 1.21.x
    	        new Item.Properties()
    	    ));
    public static final DeferredItem<ArmorItem> EMERALD_LEGGINGS =
    		ARMOR.register("emerald_leggings", () -> new ArmorItem(
    	        CustTier.EMERALD_ARMOR,            // Holder<ArmorMaterial>
    	        ArmorItem.Type.LEGGINGS,             // new enum in 1.21.x
    	        new Item.Properties()
    	    ));
    public static final DeferredItem<ArmorItem> EMERALD_BOOTS =
    		ARMOR.register("emerald_boots", () -> new ArmorItem(
    	        CustTier.EMERALD_ARMOR,            // Holder<ArmorMaterial>
    	        ArmorItem.Type.BOOTS,             // new enum in 1.21.x
    	        new Item.Properties()
    	    ));
    public static void register(IEventBus modEventBus) {
    	ARMOR.register(modEventBus);
    }
}
