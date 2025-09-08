package com.zaphaos.runolith.events;

import com.zaphaos.runolith.equipment.mod_tools.ModTools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
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
}