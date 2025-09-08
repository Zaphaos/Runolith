package com.zaphaos.runolith.equipment;

import com.zaphaos.runolith.equipment.mod_armor.ModArmor;
import com.zaphaos.runolith.equipment.mod_tools.ModTools;
import com.zaphaos.runolith.equipment.mod_weapons.ModWeapons;

import net.neoforged.bus.api.IEventBus;

public class ModEquipment {
	public static void register(IEventBus eventBus) {
        ModArmor.register(eventBus);
        ModTools.register(eventBus);
        ModWeapons.register(eventBus);
        // Later: ModWeapons.register(eventBus);
    }
}
