package com.zaphaos.runolith.events;

import com.zaphaos.runolith.datamaps.ModDataMaps;
import com.zaphaos.runolith.datamaps.imbuement.ImbuementFuel;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class ModEvents {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (!player.getPersistentData().getBoolean("emeraldtools_given")) {
            // only really need to start with a pickaxe rn
            player.addItem(new ItemStack(ModTools.EMERALD_PICKAXE.get()));

            player.getPersistentData().putBoolean("emeraldtools_given", true);
        }
    }
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        ImbuementFuel fuel = item.builtInRegistryHolder().getData(ModDataMaps.IMBUEMENT_FUEL);
        if (fuel != null) {
            event.getToolTip().add(
                Component.translatable("tooltip.runolith.imbuement_fuel_tier", fuel.tier(), "Runolith")
                        .withStyle(ChatFormatting.AQUA)
            );
        }
    }
}