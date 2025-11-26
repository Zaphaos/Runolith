package com.zaphaos.runolith.spell_system.glyphs.form;

import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.SpellContext;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BoltGlyph implements ISpellPart {

    @Override
    public void applyTargeting(SpellContext ctx) {
        if (ctx.caster == null) return;

        // Simple raytrace to pick a single target
        Vec3 eyePos = ctx.caster.getEyePosition(1f);
        Vec3 look = ctx.caster.getLookAngle();
        double range = ctx.range > 0 ? ctx.range : 10; // default 10 blocks

        // Get first entity along the line of sight
        AABB box = new AABB(eyePos, eyePos.add(look.scale(range))).inflate(1.0);
        ctx.target = ctx.caster.level().getEntitiesOfClass(LivingEntity.class, box, e -> e != ctx.caster && e.isAlive() && !e.isInvisible() && !e.isAlliedTo(ctx.caster))
                .stream()
                .min((a, b) -> Double.compare(a.distanceToSqr(eyePos), b.distanceToSqr(eyePos)))
                .orElse(null);
    }

    @Override
    public String describe(SpellContext ctx) {
        return "Shoots a bolt at a single target in front of the caster";
    }
}

