package com.zaphaos.runolith.spell_system;

import java.util.List;
import java.util.Objects;

public final class SpellData {
    public final String mainGlyph;
    public final String formGlyph;
    public final String secondaryGem;
    public final List<String> extraGlyphs;

    public SpellData(String mainGlyph, String formGlyph, String secondaryGem, List<String> extraGlyphs) {
        this.mainGlyph = mainGlyph;
        this.formGlyph = formGlyph;
        this.secondaryGem = secondaryGem;
        this.extraGlyphs = List.copyOf(extraGlyphs); // immutable copy
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpellData other)) return false;
        return Objects.equals(mainGlyph, other.mainGlyph)
            && Objects.equals(formGlyph, other.formGlyph)
            && Objects.equals(secondaryGem, other.secondaryGem)
            && Objects.equals(extraGlyphs, other.extraGlyphs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainGlyph, formGlyph, secondaryGem, extraGlyphs);
    }
}