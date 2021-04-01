package com.github.AwesomeKalin.EmeraldBankSponge;

import com.github.AwesomeKalin.EmeraldBankSponge.commands.*;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.File;

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

    PluginContainer plugin;

    @Inject
    private Logger logger;

    @Inject
    public Main(Metrics.Factory metricsFactory){
        int pluginId = 10753;
        Metrics metrics = metricsFactory.make(pluginId);
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File bankFile;

    @Inject
    @DefaultConfig(sharedRoot = true)
    ConfigurationLoader<CommentedConfigurationNode> bankManager;

    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        logger.info("[EmeraldBank] Welcome to EmeraldBank! I hope you are ready to start banking with those emeralds!");
        logger.info("[EmeraldBank] Preparing commands");
        CommandSpec help = CommandSpec.builder()
                .description(Text.of("Shows help menu (More detailed by the one done by /help EmeraldBank-Sponge)"))
                .permission("emeraldbank-sponge.commands.help")
                .executor(new Help())
                .build();
        CommandSpec new1 = CommandSpec.builder()
            .description(Text.of("Make a new bank"))
            .permission("emeraldbank-sponge.commands.new")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.player(Text.of("name")))
            )
            .executor(new New())
            .build();
        CommandSpec deposit = CommandSpec.builder()
                .description(Text.of("Deposit emeralds into the specified bank"))
                .permission("emeraldbank-sponge.commands.deposit")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("emeralds"))),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("iron")))
                )
                .executor(new Deposit())
                .build();
        CommandSpec atm = CommandSpec.builder()
                .description(Text.of("Take emeralds and/or iron out of the specified bank"))
                .permission("emeraldbank-sponge.commands.atm")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("emeralds"))),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("iron")))
                )
                .executor(new Atm())
                .build();
        CommandSpec noArgs = CommandSpec.builder()
                .description(Text.of("Throws an error. Instead do /eb help to see what to do"))
                .executor(new NoArgs())
                .child(help, "help")
                .child(new1, "new")
                .child(deposit, "deposit")
                .child(atm, "atm")
                .build();

        Sponge.getCommandManager().register(plugin, noArgs, "emeraldbank", "eb", "bank", "emerald", "banking");
        logger.info("[EmeraldBank] Commands prepared!");
        logger.info("[EmeraldBank] Preparing banks!");
        ConfigurationManager.getInstance().setup(bankFile, bankManager);
        logger.info("[EmeraldBank] Banks prepared!");
        logger.info("[EmeraldBank] Preparing Metrics!");
        logger.info("[EmeraldBank] Metrics prepared!");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

    }
}
