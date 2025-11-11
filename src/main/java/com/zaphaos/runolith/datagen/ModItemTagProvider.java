package com.zaphaos.runolith.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.equipment.mod_armor.ModArmor;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;
import com.zaphaos.runolith.equipment.mod_weapons.ModWeapons;
import com.zaphaos.runolith.item.ModItems;
import com.zaphaos.runolith.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {

	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(Tags.Items.GEMS)
			.add(ModItems.RUBY.get());
		
		tag(ModTags.Items.GROWABLE_GEMS)
			.add(ModItems.RUBY.get())
			.add(Items.EMERALD)
			.add(Items.DIAMOND);
		
		tag(ItemTags.SWORDS)
			.add(ModWeapons.EMERALD_SWORD.get());
		tag(ItemTags.PICKAXES)
			.add(ModTools.EMERALD_PICKAXE.get());
		tag(ItemTags.AXES)
			.add(ModTools.EMERALD_AXE.get());
		tag(ItemTags.SHOVELS)
			.add(ModTools.EMERALD_SHOVEL.get());
		tag(ItemTags.HOES)
			.add(ModTools.EMERALD_HOE.get());
		
		this.tag(ItemTags.TRIMMABLE_ARMOR)
        	.add(ModArmor.EMERALD_HELMET.get())
        	.add(ModArmor.EMERALD_CHESTPLATE.get())
        	.add(ModArmor.EMERALD_LEGGINGS.get())
        	.add(ModArmor.EMERALD_BOOTS.get());
		tag(ItemTags.HEAD_ARMOR)
			.add(ModArmor.EMERALD_HELMET.get());
		tag(ItemTags.CHEST_ARMOR)
			.add(ModArmor.EMERALD_CHESTPLATE.get());
		tag(ItemTags.LEG_ARMOR)
    		.add(ModArmor.EMERALD_LEGGINGS.get());
		tag(ItemTags.FOOT_ARMOR)
			.add(ModArmor.EMERALD_BOOTS.get());
	}

}
