package com.zaphaos.runolith;

import java.util.HashMap;
import java.util.Map;

import com.zaphaos.runolith.spell_system.ISpellPart;
import com.zaphaos.runolith.spell_system.glyphs.*;
import com.zaphaos.runolith.spell_system.glyphs.form.*;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = Runolith.MOD_ID)
public class SpellRegistry {

    public static final Map<String, ISpellPart> PARTS = new HashMap<>();
    
    public static void register(String id, ISpellPart part) {
        PARTS.put(id, part);
    }

    public static ISpellPart get(String id) {
        return PARTS.get(id);
    }
    
    @SubscribeEvent
    public static void registerGlyphs(RegisterEvent event) {
    	// Main Gems
    	register("diamond_gem", new DiamondGem());
    	
    	// Form Glyphs
    	register("self_form", new SelfGlyph());
    	
    	// Secondary Gems
    	register("amethyst_gem", new AmethystGem());
    	register("quartz_gem", new QuartzGem());
    }
}

