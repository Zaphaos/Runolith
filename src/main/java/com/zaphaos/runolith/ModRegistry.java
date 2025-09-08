package com.zaphaos.runolith;

import net.neoforged.bus.api.IEventBus;
import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.block.entity.ModBlockEntities;
import com.zaphaos.runolith.equipment.ModEquipment;
import com.zaphaos.runolith.item.ModItems;
import com.zaphaos.runolith.recipe.ModRecipes;
import com.zaphaos.runolith.screen.ModMenuTypes;

public class ModRegistry {
    public static void register(IEventBus eventBus) {
        CreativeTab.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEquipment.register(eventBus);
        CustTier.ARMOR_MATERIALS.register(eventBus);
    }
}