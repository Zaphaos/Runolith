package com.zaphaos.runolith.spell_system;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import com.zaphaos.runolith.attachment.ModAttachments;

public class PlayerSpellData {

    public static List<String> getKnownParts(Player player) {
        return player.getData(ModAttachments.KNOWN_SPELL_PARTS.get());
    }

    public static void learn(Player player, String partId) {
        learn(player, partId, false);
    }

    public static void learn(Player player, String partId, boolean initial) {
        List<String> list = getKnownParts(player);
        if (!list.contains(partId)) {
            list.add(partId);
            
         // Only send messages from the server side
            if (player instanceof ServerPlayer serverPlayer && !initial) {
                serverPlayer.sendSystemMessage(
                    Component.translatable("message.runolith.learn_spell", partId)
                );
            }
        }
    }

    public static boolean knows(Player player, String partId) {
        return getKnownParts(player).contains(partId);
    }
}
