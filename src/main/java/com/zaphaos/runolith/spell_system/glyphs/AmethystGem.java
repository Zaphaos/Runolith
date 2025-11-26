package com.zaphaos.runolith.spell_system.glyphs;

import com.zaphaos.runolith.spell_system.IGemCost;
import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AmethystGem implements ISpellPart, IGemCost {

    @Override
    public void applyEffects(SpellContext ctx) {
        if (ctx.secondaryGem == this) {
            ctx.flags.add("use_alt_main_effect");
            return;
        }
        if (ctx.extraGlyphs.contains(this)) {
            ctx.basePower += 0.25f;
        }
    }

    @Override
    public ItemStack getCost() {
        return new ItemStack(Items.AMETHYST_SHARD);
    }
    
    @Override
    public String describe(SpellContext ctx) {
        if (ctx.secondaryGem == this)
            return "Alters main gem to use its alternate effect";
        if (ctx.extraGlyphs.contains(this))
            return "Adds +0.25 power";
        return null;
    }
    
    @Override
	public int getRarity() { return 1; }

}

