package com.zaphaos.runolith.loot_modifiers;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;
import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.spell_system.loot.GlyphLootModifier;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Runolith.MOD_ID);

    public static final Supplier<MapCodec<GlyphLootModifier>> GLYPH_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("glyph_loot", () -> GlyphLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
    	GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}

