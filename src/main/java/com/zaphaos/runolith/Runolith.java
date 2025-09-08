package com.zaphaos.runolith;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

import com.zaphaos.runolith.events.ModEvents;

import net.neoforged.bus.api.IEventBus;

@Mod(Runolith.MODID)
public class Runolith 
{
    public static final String MODID = "runolith";

    public Runolith(IEventBus modEventBus) 
    {
    	ModRegistry.register(modEventBus);
        NeoForge.EVENT_BUS.register(new ModEvents());
    }
}