package com.zaphaos.runolith.item;

import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellData;
import com.zaphaos.runolith.ModDataComponents;
import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.SpellRegistry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.registries.DeferredRegister;

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
    	
    	if (true) {
    		
    	}
    	
		return InteractionResult.SUCCESS;
    	
    }
    
    /** Creates a new page with a glyph assigned. */
    public static ItemStack createPage(String glyphId) {
        ItemStack stack = new ItemStack(ModItems.SPELL_PAGE.get());
        setGlyph(stack, glyphId);
        return stack;
    }

    /** Sets a glyph ID on the page. */
    public static void setGlyph(ItemStack stack, String glyphId) {
    	stack.set(ModDataComponents.GLYPH_ID.get(), glyphId);
    }

    /** Returns the glyph ID stored on this page, or null if none. */
    public static String getGlyph(ItemStack stack) {
        return stack.getComponents().getOrDefault(ModDataComponents.GLYPH_ID.get(), null);
    }

    /** Checks if the page has a glyph assigned. */
    public static boolean hasGlyph(ItemStack stack) {
        return stack.getComponents().has(ModDataComponents.GLYPH_ID.get());
    }

    public static ISpellPart getRandomGlyph() {
    	return getRandomGlyph(0, 5);
    }
    public static ISpellPart getRandomGlyph(int maxRarity) {
    	return getRandomGlyph(0, maxRarity);
    }
    public static ISpellPart getRandomGlyph(int minRarity, int maxRarity) {
        List<ISpellPart> candidates = SpellRegistry.PARTS.values().stream()
            .filter(g -> g.getRarity() > 0)
            .filter(g -> g.getRarity() <= maxRarity)
            .toList();

        if (candidates.isEmpty()) return null;

        return candidates.get(new Random().nextInt(candidates.size()));
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        String glyphId = getGlyph(stack);
        if (glyphId == null) {
            tooltip.add(Component.translatable("tooltip.runolith.empty_spell_slate"));
            return;
        }
        
        tooltip.add(Component.translatable("tooltip.runolith.glyph_main", glyphId));
    }

}
