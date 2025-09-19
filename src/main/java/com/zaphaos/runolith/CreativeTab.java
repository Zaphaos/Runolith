package com.zaphaos.runolith;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;
import com.zaphaos.runolith.equipment.mod_armor.ModArmor;
import com.zaphaos.runolith.item.ModItems;
import com.zaphaos.runolith.equipment.mod_weapons.ModWeapons;

public class CreativeTab {
	public static final DeferredRegister<CreativeModeTab> Runolith_TAB =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Runolith.MODID);
	
	public static final Supplier<CreativeModeTab> RUNOLITH_GENERAL_Tab = Runolith_TAB.register("runolith_tab", 
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModWeapons.EMERALD_SWORD.get()))
			.title(Component.translatable("creativetab.runolith.runolith_tab"))
			.displayItems((itemDisplayParameters, output) -> {
				output.accept(ModBlocks.ENRICHMENT_CHAMBER.get());
				output.accept(ModBlocks.GROWTH_CHAMBER.get());
				output.accept(ModBlocks.IMBUEMENT_CHAMBER.get());
				output.accept(ModItems.GEM_NODE_EMERALD.get());
				output.accept(ModWeapons.EMERALD_SWORD.get());
				output.accept(ModTools.EMERALD_PICKAXE.get());
				output.accept(ModTools.EMERALD_AXE.get());
				output.accept(ModTools.EMERALD_SHOVEL.get());
				output.accept(ModTools.EMERALD_HOE.get());
				output.accept(ModArmor.EMERALD_HELMET.get());
				output.accept(ModArmor.EMERALD_CHESTPLATE.get());
				output.accept(ModArmor.EMERALD_LEGGINGS.get());
				output.accept(ModArmor.EMERALD_BOOTS.get());
				output.accept(ModBlocks.RUBY_BLOCK.get());
				output.accept(ModBlocks.IMPURE_RUBY_BLOCK.get());
				output.accept(ModBlocks.RUBY_ORE.get());
				output.accept(ModBlocks.DEEPSLATE_RUBY_ORE.get());
				output.accept(ModItems.RUBY.get());
				output.accept(ModItems.IMPURE_RUBY.get());
				output.accept(ModItems.EMPTY_RUBY.get());
				output.accept(ModItems.GEM_NODE_RUBY.get());
				output.accept(ModItems.GEM_NODE_DIAMOND.get());
			}).build());
	
	public static void register(IEventBus eventBus) {
		Runolith_TAB.register(eventBus);
	}
}