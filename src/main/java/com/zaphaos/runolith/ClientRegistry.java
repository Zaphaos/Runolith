package com.zaphaos.runolith;

import com.zaphaos.runolith.screen.ModMenuTypes;
import com.zaphaos.runolith.screen.custom.EnrichmentChamberScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Runolith.MODID, value = Dist.CLIENT)
public class ClientRegistry {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ENRICHMENT_CHAMBER_MENU.get(), EnrichmentChamberScreen::new);
    }
}