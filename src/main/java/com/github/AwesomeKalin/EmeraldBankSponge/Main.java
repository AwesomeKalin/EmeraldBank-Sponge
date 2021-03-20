package com.github.AwesomeKalin.EmeraldBankSponge;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import com.github.AwesomeKalin.EmeraldBankSponge.Metrics;

@Plugin(
        id = "emeraldbank-sponge",
        name = "EmeraldBank-Sponge",
        description = "A bank for your emeralds",
        url = "https://github.com/AwesomeKalin/EmeraldBank-Sponge",
        authors = {
                "AwesomeKalin55"
        }
)
public class Main {

    private final Metrics metrics;

    @Inject
    private Logger logger;

    @Inject
    public Main(Metrics.Factory metricsFactory){
        int pluginId = 10753;
        metrics = metricsFactory.make(pluginId);
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("[EmeraldBank] Welcome to EmeraldBank! I hope you are ready to start banking with those emeralds!");
        logger.info("[EmeraldBank] Preparing commands");
        logger.info("[EmeraldBank] Commands prepared!");
        logger.info("[EmeraldBank] Preparing banks!");
        logger.info("[EmeraldBank] Banks prepared!");
        logger.info("[EmeraldBank] Preparing Metrics!");
        logger.info("[EmeraldBank] Metrics prepared!");
    }
}
