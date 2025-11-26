package com.zaphaos.runolith.events;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.zaphaos.runolith.SpellRegistry;
import com.zaphaos.runolith.datamaps.ModDataMaps;
import com.zaphaos.runolith.datamaps.imbuement.ImbuementFuel;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;
import com.zaphaos.runolith.item.ModItems;
import com.zaphaos.runolith.item.SpellSlateItem;
import com.zaphaos.runolith.spell_system.PlayerSpellData;
import com.zaphaos.runolith.spell_system.SpellData;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class ModEvents {

    // Track players by UUID to avoid memory leaks
	private final Set<ServerPlayer> pendingSlate = new HashSet<>();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // Give tools immediately if not already given
        if (!player.getPersistentData().getBoolean("emeraldtools_given")) {
            player.addItem(new ItemStack(ModTools.EMERALD_PICKAXE.get()));
            player.getPersistentData().putBoolean("emeraldtools_given", true);
        }

        // Schedule player to receive spell slates
        pendingSlate.add(player);
        PlayerSpellData.learn(player, "self_form");
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : new HashSet<>(pendingSlate)) {
            // Registry readiness check
            if (SpellRegistry.get("self_form") == null
                || SpellRegistry.get("diamond_gem") == null
                || SpellRegistry.get("amethyst_gem") == null
                || SpellRegistry.get("quartz_gem") == null) {
                continue;
            }

            // Give first slate
            ItemStack slate1 = new ItemStack(ModItems.SPELL_SLATE.get());
            SpellData data1 = new SpellData("diamond_gem", "self_form", "amethyst_gem", List.of());
            SpellSlateItem.setSpell(slate1, data1);
            player.addItem(slate1);

            // Give second slate
            ItemStack slate2 = new ItemStack(ModItems.SPELL_SLATE.get());
            SpellData data2 = new SpellData("diamond_gem", "self_form", "quartz_gem", List.of());
            SpellSlateItem.setSpell(slate2, data2);
            player.addItem(slate2);

            // Remove player from pending
            pendingSlate.remove(player);
            
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
