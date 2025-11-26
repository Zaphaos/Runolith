package com.zaphaos.runolith.item;

import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.PlayerSpellData;
import com.zaphaos.runolith.spell_system.SpellComponents;
import com.zaphaos.runolith.spell_system.SpellData;
import com.zaphaos.runolith.SpellRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;
import java.util.Random;

public class SpellPageItem extends Item {

    public SpellPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getPlayer() instanceof ServerPlayer player)) return InteractionResult.PASS;

        ItemStack stack = context.getItemInHand();

        // Read glyph ID from the DataComponent
        if (!stack.getComponents().has(SpellComponents.SPELL())) {
            return InteractionResult.PASS; // nothing to study
        }

        SpellData data = stack.getComponents().get(SpellComponents.SPELL());
        if (data == null || data.mainGlyph == null || data.mainGlyph.isEmpty()) {
            return InteractionResult.PASS; // nothing to study
        }

        String glyphId = data.mainGlyph;

        if (SpellRegistry.get(glyphId) == null) {
            player.sendSystemMessage(Component.literal("Unknown glyph: " + glyphId));
            return InteractionResult.FAIL;
        }

        if (PlayerSpellData.knows(player, glyphId)) {
            player.sendSystemMessage(Component.literal("You already know this glyph: " + glyphId));
            return InteractionResult.FAIL;
        }

        // Teach the player
        PlayerSpellData.learn(player, glyphId);
        player.sendSystemMessage(Component.literal("You studied the glyph: " + glyphId));

        // Consume the page
        stack.shrink(1);

        return InteractionResult.SUCCESS;
    }
    
    public static ItemStack createPage(String glyphId, Item pageItem) {
        ItemStack stack = new ItemStack(pageItem);

        stack.update(
            SpellComponents.SPELL(),
            new SpellData("", "", "", List.of()), // default value
            old -> new SpellData(glyphId, "", "", List.of()) // set mainGlyph
        );

        return stack;
    }
    
    public static ISpellPart getRandomGlyph(int maxRarity) {
        List<ISpellPart> candidates = SpellRegistry.PARTS.values().stream()
            .filter(g -> g.getRarity() > 0)
            .filter(g -> g.getRarity() <= maxRarity)
            .toList();

        if (candidates.isEmpty()) return null;

        return candidates.get(new Random().nextInt(candidates.size()));
    }
}
