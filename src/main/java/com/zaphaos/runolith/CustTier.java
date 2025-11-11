package com.zaphaos.runolith;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.EnumMap;
import java.util.List;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;

public class CustTier {

    // ----------------------
    // TOOL TIER
    // ----------------------
	public static final Tier EMERALD_TIER = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL,  // tag for blocks that can't be mined by this tier
		    99,                                 // durability (uses)
		    14.0f,                              // mining speed
		    1.5f,                               // attack damage bonus
		    25,                                 // enchantability
		    () -> Ingredient.of(Items.EMERALD)  // repair ingredient (lazy)
		);

    // ----------------------
    // ARMOR MATERIAL
    // ----------------------
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, Runolith.MOD_ID);
	public static final Holder<ArmorMaterial> EMERALD_ARMOR =
		ARMOR_MATERIALS.register("emerald", () -> new ArmorMaterial( Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
	            map.put(ArmorItem.Type.BOOTS, 2);
	            map.put(ArmorItem.Type.LEGGINGS, 5);
	            map.put(ArmorItem.Type.CHESTPLATE, 5);
	            map.put(ArmorItem.Type.HELMET, 2);
	            map.put(ArmorItem.Type.BODY, 4);
	        }),									// Armour value
		    25,                                 // enchantability
		    SoundEvents.ARMOR_EQUIP_DIAMOND,    // equip sound wrapped as Holder<SoundEvent> (note below)
		    () -> Ingredient.of(Items.EMERALD), // repair ingredient
		    List.of(new ArmorMaterial.Layer(
                    ResourceLocation.fromNamespaceAndPath(Runolith.MOD_ID, "emerald")
                )), // layers – common listing
		    1.0f,                               // toughness
		    0.0f                                // knockback resistance
		));
}