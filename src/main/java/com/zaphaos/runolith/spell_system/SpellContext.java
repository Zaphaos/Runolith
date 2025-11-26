package com.zaphaos.runolith.spell_system;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SpellContext {
    public Player caster;
    public LivingEntity target;
    public ISpellPart mainGlyph;
    public ISpellPart formGlyph;

    public ISpellPart secondaryGem; // gem that adds effect for form glyph

    // Initialize lists to avoid NPE
    public List<ISpellPart> extraGlyphs = new ArrayList<>();
    public List<String> flags = new ArrayList<>();

    public double basePower;
    public double range;
    public double duration;

    public List<Consumer<ServerLevel>> executeSteps = new ArrayList<>();
    
    public List<String> description = new ArrayList<>();
}
