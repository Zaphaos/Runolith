package com.zaphaos.runolith.spell_system;

public interface ISpellPart {
    default void applyStats(SpellContext ctx) {}
    default void applyTargeting(SpellContext ctx) {}
    default void applyEffects(SpellContext ctx) {}
    default void onCast(SpellContext ctx) {}
    default String describe(SpellContext ctx) { return null; }
    default String getName() {
        String name = this.getClass().getSimpleName();

        // Remove Glyph suffix, but keep Gem suffix
        if (name.endsWith("Glyph")) {
            name = name.substring(0, name.length() - "Glyph".length());
        }

        // Convert CamelCase to snake_case
        String snake = name
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();

        return snake;
    }
    default String getTranslationKey() {
        return "glyph.runolith." + getName(); // e.g., "spell.glyph.diamond_gem"
    }
    default int getRarity() { return 1; }
}