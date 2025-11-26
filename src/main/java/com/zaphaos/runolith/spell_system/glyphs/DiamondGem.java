package com.zaphaos.runolith.spell_system.glyphs;

import com.zaphaos.runolith.spell_system.IGemCost;
import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondGem implements ISpellPart, IGemCost {

    @Override
    public void applyStats(SpellContext ctx) {
        ctx.basePower += 3;
    }

    @Override
    public void applyEffects(SpellContext ctx) {
        boolean alt = ctx.flags.contains("use_alt_main_effect");

        if (!alt) {
            // Damage spell
            ctx.executeSteps.add(level -> {
                if (ctx.target != null) {
                	ctx.target.hurt(
                		    level.damageSources().magic(),
                		    (float) ctx.basePower
                		);
                }
            });
            ctx.description.add("Deals " + ctx.basePower + " magic damage to target");
        } else {
            // Buff spell
            ctx.executeSteps.add(level -> {
                if (ctx.caster != null) {
                    ctx.caster.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.DAMAGE_BOOST,
                        20 * 10, // 10 seconds
                        1
                    ));
                }
            });
            ctx.description.add("Applies Strength II for 10 seconds");
        }
    }
    
    @Override
    public ItemStack getCost() {
        return new ItemStack(Items.DIAMOND);
    }
    
    @Override
    public String describe(SpellContext ctx) {
        boolean alt = ctx.flags.contains("use_alt_main_effect");
        if (!alt)
            return "Deals " + ctx.basePower + " magic damage to the target";
        else
            return "Applies Strength II for 10 seconds";
    }
    
    @Override
    public String getTranslationKey() {
        return "glyph.runolith.diamond_gem";
    }
    
    @Override
	public int getRarity() { return 2; }

}
