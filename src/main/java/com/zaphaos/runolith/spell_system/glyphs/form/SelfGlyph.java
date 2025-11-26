package com.zaphaos.runolith.spell_system.glyphs.form;

import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellContext;

public class SelfGlyph implements ISpellPart {

	@Override
	public void applyTargeting(SpellContext ctx) {
	    ctx.target = ctx.caster;  // self-targeting
	}
	
	@Override
	public String describe(SpellContext ctx) {
	    return "Targets the caster";
	}
	
	@Override
	public int getRarity() { return 0; }

}

