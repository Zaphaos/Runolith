package com.zaphaos.runolith.spell_system;

import java.util.ArrayList;
import java.util.List;

import com.zaphaos.runolith.SpellRegistry;

public class SpellBuilder {

    private String primaryGem;
    private String form;
    private String secondaryGem;
    private final List<String> extraGlyphs = new ArrayList<>();

    public static SpellBuilder begin() {
        return new SpellBuilder();
    }

    public SpellBuilder primary(String id) {
        this.primaryGem = id;
        return this;
    }

    public SpellBuilder form(String id) {
        this.form = id;
        return this;
    }

    public SpellBuilder secondary(String id) {
        this.secondaryGem = id;
        return this;
    }

    public SpellBuilder addGlyph(String id) {
        this.extraGlyphs.add(id);
        return this;
    }

    public SpellData build() {
        return new SpellData(primaryGem, form, secondaryGem, List.copyOf(extraGlyphs));
    }
    
    public SpellData buildChecked() {
        if (SpellRegistry.get(primaryGem) == null)
            throw new IllegalStateException("Primary gem '" + primaryGem + "' not registered.");

        if (SpellRegistry.get(form) == null)
            throw new IllegalStateException("Form '" + form + "' not registered.");

        if (secondaryGem != null && SpellRegistry.get(secondaryGem) == null)
            throw new IllegalStateException("Secondary gem '" + secondaryGem + "' not registered.");

        for (String glyph : extraGlyphs) {
            if (SpellRegistry.get(glyph) == null)
                throw new IllegalStateException("Extra glyph '" + glyph + "' not registered.");
        }

        return build();
    }
}