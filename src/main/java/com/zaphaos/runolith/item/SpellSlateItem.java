package com.zaphaos.runolith.item;

import java.util.List;

import javax.annotation.Nullable;

import com.zaphaos.runolith.SpellRegistry;
import com.zaphaos.runolith.spell_system.*;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SpellSlateItem extends Item {

	public SpellSlateItem(Properties properties) {
		super(properties);
	}
	
	public static void setSpell(ItemStack stack, SpellData data) {
		stack.update(
			    SpellComponents.SPELL(),
			    new SpellData("", "", "", List.of()), // default immutable value
			    old -> data
			);
	}

    @Nullable
    public static SpellData getSpell(ItemStack stack) {
        if (!stack.getComponents().has(SpellComponents.SPELL())) {
            return null;
        }
        return stack.getComponents().get(SpellComponents.SPELL());
    }
	
	@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

        SpellData data = getSpell(stack);
        if (data == null) return InteractionResultHolder.pass(stack);

        // If empty slate → open GUI
        /*if (data == null || data.mainGlyph == null || data.mainGlyph.isEmpty()) {

            if (level.isClientSide()) {
                Minecraft.getInstance().setScreen(new SpellSelectionScreen(stack));
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }*/

        // If spell exists → cast it
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(stack);
        }
        
     // Fetch the spell parts
        ISpellPart main = SpellRegistry.get(data.mainGlyph);
        ISpellPart form = SpellRegistry.get(data.formGlyph);
        ISpellPart secondaryGem = SpellRegistry.get(data.secondaryGem);

        if (main == null || form == null || secondaryGem == null) {
            player.displayClientMessage(Component.translatable("message.runolith.warning.spell_part_mis"), true);
            return InteractionResultHolder.fail(stack);
        }

        // Build the spell context
        SpellContext ctx = SpellEngine.buildSpell(
                player,
                main,
                form,
                secondaryGem,
                data.extraGlyphs.stream().map(SpellRegistry::get).toList()
        );

        // Validate glyphs
        if (ctx.mainGlyph == null || ctx.formGlyph == null) {
            player.displayClientMessage(Component.translatable("message.runolith.warning.invalid_spell"), true);
            return InteractionResultHolder.fail(stack);
        }

        // Check required gems
        if (!hasRequiredGems(player, data)) {
            player.displayClientMessage(Component.translatable("message.runolith.warning.materials_mis"), true);
            return InteractionResultHolder.fail(stack);
        }

        // Consume gems
        consumeRequiredGems(player, data);

        // Execute spell steps
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            SpellEngine.executeSpell(ctx, serverLevel);
        }

        return InteractionResultHolder.success(stack);
    }
    
    private boolean hasRequiredGems(Player player, SpellData data) {
        return hasGem(player, data.mainGlyph)
            && hasGem(player, data.secondaryGem);
    }

    private boolean hasGem(Player player, String gemId) {
        ISpellPart part = SpellRegistry.get(gemId);
        if (!(part instanceof IGemCost cost))
            return true; // no cost required

        ItemStack required = cost.getCost();

        return player.getInventory().contains(required);
    }

    private void consumeRequiredGems(Player player, SpellData data) {
        consumeGem(player, data.mainGlyph);
        consumeGem(player, data.secondaryGem);
    }

    private void consumeGem(Player player, String gemId) {
        ISpellPart part = SpellRegistry.get(gemId);
        if (!(part instanceof IGemCost cost)) return;

        ItemStack required = cost.getCost();
        int needed = required.getCount();
        Item requiredItem = required.getItem();

        for (int i = 0; i < player.getInventory().getContainerSize() && needed > 0; i++) {
            ItemStack slot = player.getInventory().getItem(i);

            if (slot.getItem() == requiredItem) {
                int remove = Math.min(needed, slot.getCount());
                slot.shrink(remove);
                needed -= remove;
            }
        }
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        SpellData data = getSpell(stack);
        if (data == null) {
            tooltip.add(Component.translatable("tooltip.runolith.empty_spell_slate"));
            return;
        }

        // Helper function to get translated name from glyph ID
        java.util.function.Function<String, Component> translateGlyph = id -> {
            ISpellPart part = SpellRegistry.get(id);
            if (part == null) return Component.literal(id); // fallback to raw string
            return Component.translatable(part.getTranslationKey());
        };

     // Main glyph
        if (data.mainGlyph != null && !data.mainGlyph.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.runolith.glyph_main", translateGlyph.apply(data.mainGlyph)));
        }

        // Form glyph
        if (data.formGlyph != null && !data.formGlyph.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.runolith.glyph_form", translateGlyph.apply(data.formGlyph)));
        }

        // Secondary gem
        if (data.secondaryGem != null && !data.secondaryGem.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.runolith.glyph_secondary", translateGlyph.apply(data.secondaryGem)));
        }

     // Extra glyphs
        if (data.extraGlyphs != null && !data.extraGlyphs.isEmpty()) {
            List<Component> extrasComponents = data.extraGlyphs.stream().map(translateGlyph).toList();
            String extrasString = extrasComponents.stream()
                .map(Component::getString)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
            tooltip.add(Component.translatable("tooltip.runolith.glyph_extra", extrasString));
        }

    }

}
