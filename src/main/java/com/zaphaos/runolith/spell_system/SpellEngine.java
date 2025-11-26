package com.zaphaos.runolith.spell_system;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class SpellEngine {
	public static SpellContext buildSpell(Player player,
            ISpellPart mainGlyph,
            ISpellPart formGlyph,
            ISpellPart secondaryGem,
            List<ISpellPart> extraGlyphs) {

			SpellContext ctx = new SpellContext();
			ctx.caster = player;
			ctx.mainGlyph = mainGlyph;
			ctx.formGlyph = formGlyph;
			ctx.secondaryGem = secondaryGem;
			ctx.extraGlyphs = extraGlyphs;
			
			// Defaults
			ctx.basePower = 2;
			ctx.duration = 10;
			if (ctx.flags == null) ctx.flags = new ArrayList<>();
			
			mainGlyph.applyStats(ctx);
			formGlyph.applyStats(ctx);
			formGlyph.applyTargeting(ctx);
			secondaryGem.applyEffects(ctx);
			mainGlyph.applyEffects(ctx);
			for (ISpellPart g : extraGlyphs) g.applyEffects(ctx);
			
			
			
			return ctx;
			}


/**
* Executes the spell steps stored in the context.
*/
	public static void executeSpell(SpellContext ctx, ServerLevel level) {
		debugSpell(ctx);
		for (var step : ctx.executeSteps)
			step.accept(level);
	}
	
	private static void debugSpell(SpellContext ctx) {
	    Player p = ctx.caster;

	    p.displayClientMessage(
	        Component.literal("=== Spell Debug ===").withStyle(ChatFormatting.GOLD),
	        false
	    );

	    p.displayClientMessage(Component.literal("Main Glyph: " + ctx.mainGlyph.getName()), false);
	    p.displayClientMessage(Component.literal("Form Glyph: " + ctx.formGlyph.getName()), false);
	    p.displayClientMessage(Component.literal("Secondary Gem: " + ctx.secondaryGem.getName()), false);

	    if (!ctx.extraGlyphs.isEmpty()) {
	        p.displayClientMessage(
	            Component.literal("Extra Glyphs: " +
	                ctx.extraGlyphs.stream().map(ISpellPart::getName).toList()
	            ), false
	        );
	    }

	    for (String f : ctx.flags)
	        p.displayClientMessage(Component.literal("Flag: " + f), false);

	    p.displayClientMessage(Component.literal("Power: " + ctx.basePower), false);
	    p.displayClientMessage(Component.literal("Range: " + ctx.range), false);
	    p.displayClientMessage(Component.literal("Duration: " + ctx.duration), false);

	    // NEW: List what it will actually do
	    p.displayClientMessage(
	        Component.literal("--- Effects ---").withStyle(ChatFormatting.AQUA),
	        false
	    );

	    if (ctx.description.isEmpty()) {
	        p.displayClientMessage(Component.literal("No effects registered?"), false);
	    } else {
	        for (String d : ctx.description) {
	            p.displayClientMessage(Component.literal(" - " + d), false);
	        }
	    }
	}

}
