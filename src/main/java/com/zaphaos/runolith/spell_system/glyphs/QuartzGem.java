package com.zaphaos.runolith.spell_system.glyphs;

import com.zaphaos.runolith.spell_system.IGemCost;
import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class QuartzGem implements ISpellPart, IGemCost {

    @Override
    public void applyEffects(SpellContext ctx) {
    	if (ctx.extraGlyphs.contains(this)) {
        	ctx.basePower += 0.5;
        }
    	if (ctx.secondaryGem == this) {
    		return;
    	}
    }

    @Override
    public ItemStack getCost() {
        return new ItemStack(Items.QUARTZ);
    }
    
    @Override
    public String describe(SpellContext ctx) {
        if (ctx.secondaryGem == this)
            return "Does not effect the Spell.";
        if (ctx.extraGlyphs.contains(this))
            return "Adds +0.5 power";
        return null;
    }
    
    @Override
	public int getRarity() { return 1; }
    
}

