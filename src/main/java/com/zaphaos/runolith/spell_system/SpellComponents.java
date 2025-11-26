package com.zaphaos.runolith.spell_system;

import com.zaphaos.runolith.Runolith;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

// Codec for SpellData
public class SpellComponents {

	public static final Codec<SpellData> SPELL_CODEC = RecordCodecBuilder.create(instance ->
    	instance.group(
    		Codec.STRING.optionalFieldOf("mainGlyph", "").forGetter(sd -> sd.mainGlyph),
    		Codec.STRING.optionalFieldOf("formGlyph", "").forGetter(sd -> sd.formGlyph),
    		Codec.STRING.optionalFieldOf("secondaryGem", "").forGetter(sd -> sd.secondaryGem),
    		Codec.list(Codec.STRING)
             .optionalFieldOf("extraGlyphs", List.of())
             .forGetter(sd -> sd.extraGlyphs)
    		).apply(instance, SpellData::new)
	);

    public static final DeferredRegister.DataComponents COMPONENT_TYPES =
        DeferredRegister.DataComponents.createDataComponents(Registries.DATA_COMPONENT_TYPE, Runolith.MOD_ID);

    // Register the component (DeferredHolder)
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpellData>> SPELL_COMPONENT =
        COMPONENT_TYPES.registerComponentType("spell", builder -> builder.persistent(SPELL_CODEC));

    // Getter for the actual DataComponentType<SpellData>
    public static DataComponentType<SpellData> SPELL() {
        return SPELL_COMPONENT.get();
    }
    
}
