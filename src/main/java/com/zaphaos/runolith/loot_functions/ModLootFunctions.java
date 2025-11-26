package com.zaphaos.runolith.loot_functions;

import com.zaphaos.runolith.Runolith;
import com.zaphaos.runolith.spell_system.RandomSpellLootFunction;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModLootFunctions {

    public static final DeferredRegister<LootItemFunctionType<?>> LOOT =
        DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Runolith.MOD_ID);

    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<RandomSpellLootFunction>>
        RANDOM_SPELL_LOOT = LOOT.register("random_spell",
            () -> new LootItemFunctionType<RandomSpellLootFunction>(RandomSpellLootFunction.CODEC)
        );
}
