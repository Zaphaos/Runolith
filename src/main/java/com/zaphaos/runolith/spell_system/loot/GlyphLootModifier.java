package com.zaphaos.runolith.spell_system.loot;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zaphaos.runolith.SpellRegistry;
import com.zaphaos.runolith.item.SpellPageItem;
import com.zaphaos.runolith.spell_system.ISpellPart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class GlyphLootModifier extends LootModifier {

	public static final MapCodec<GlyphLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
	    LootModifier.codecStart(inst).and(
	        inst.group(
	            Codec.INT.fieldOf("min_rarity").forGetter(m -> m.minRarity),
	            Codec.INT.fieldOf("max_rarity").forGetter(m -> m.maxRarity),
	            Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance)
	        )
	    ).apply(inst, (conds, min, max, chance) ->
	         new GlyphLootModifier(conds, min, max, chance)
	    ));
	
    private final int minRarity;
    private final int maxRarity;
    private final float chance;

    public GlyphLootModifier(LootItemCondition[] conditions, int minRarity, int maxRarity, float chance) {
        super(conditions);
        this.minRarity = minRarity;
        this.maxRarity = maxRarity;
        this.chance = chance;
        System.out.println("GlyphLootModifier LOADED!");
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        if (context.getRandom().nextFloat() > chance)
            return generatedLoot;

        List<ISpellPart> pool = SpellRegistry.PARTS.values().stream()
                .filter(g -> g.getRarity() >= minRarity)
                .filter(g -> g.getRarity() <= maxRarity)
                .toList();

        if (pool.isEmpty())
            return generatedLoot;

        ISpellPart part = pool.get(context.getRandom().nextInt(pool.size()));

        ItemStack page = SpellPageItem.createPage(part.getName());

        generatedLoot.add(page);
        
        if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player) {
            player.sendSystemMessage(Component.literal("[GlyphLootModifier] Added glyph: " + part.getName()));
        }
        
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }


}
