package com.zaphaos.runolith.spell_system;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zaphaos.runolith.item.SpellPageItem;
import com.zaphaos.runolith.loot_functions.ModLootFunctions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.LootContext;

public class RandomSpellLootFunction implements LootItemFunction {

    public static final MapCodec<RandomSpellLootFunction> CODEC =
        RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                Codec.INT.fieldOf("max_rarity").forGetter(f -> f.maxRarity)
            ).apply(instance, RandomSpellLootFunction::new)
        );

    private final int maxRarity;

    public RandomSpellLootFunction(int maxRarity) {
        this.maxRarity = maxRarity;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        var glyph = SpellPageItem.getRandomGlyph(maxRarity);
        if (glyph != null) {
            return SpellPageItem.createPage(glyph.getName(), stack.getItem());
        }
        return stack;
    }

    @Override
    public LootItemFunctionType<?> getType() {
        return ModLootFunctions.RANDOM_SPELL_LOOT.get();
    }
}

