package com.github.AwesomeKalin.EmeraldBankSponge;

import com.github.AwesomeKalin.EmeraldBankSponge.commands.Help;
import com.github.AwesomeKalin.EmeraldBankSponge.commands.NoArgs;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    PluginContainer plugin;

    @Inject
    private Logger logger;

    @Inject
    public Main(Metrics.Factory metricsFactory){
        int pluginId = 10753;
        metrics = metricsFactory.make(pluginId);
    }

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path bankDir;

    private Path bankFile = Paths.get(bankDir + "/bank.conf");

    private ConfigurationLoader<CommentedConfigurationNode> bankLoader = HoconConfigurationLoader.builder().setPath(bankFile).build();
    private CommentedConfigurationNode bankNode;

    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        logger.info("[EmeraldBank] Welcome to EmeraldBank! I hope you are ready to start banking with those emeralds!");
        logger.info("[EmeraldBank] Preparing commands");
        CommandSpec help = CommandSpec.builder()
                .description(Text.of("Shows help menu (More detailed by the one done by /help EmeraldBank-Sponge)"))
                .permission("emeraldbank-sponge.commands.help")
                .executor(new Help())
                .build();
        CommandSpec noArgs = CommandSpec.builder()
                .description(Text.of("Throws an error. Instead do /eb help to see what to do"))
                .executor(new NoArgs())
                .child(help, "help")
                .build();

        Sponge.getCommandManager().register(plugin, noArgs, "emeraldbank", "eb", "bank", "emerald", "banking");
        logger.info("[EmeraldBank] Commands prepared!");
        logger.info("[EmeraldBank] Preparing banks!");
        if(!Files.exists(bankDir)){
            try{
                Files.createDirectories(bankDir);
            }catch(IOException io){
                io.printStackTrace();
            }
        }
        setup();
        logger.info("[EmeraldBank] Banks prepared!");
        logger.info("[EmeraldBank] Preparing Metrics!");
        logger.info("[EmeraldBank] Metrics prepared!");
    }

    public void setup(){
        if(!Files.exists(bankFile)){
            try{
                Files.createFile(bankFile);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            load();
        }
    }

    public void load(){
        try{
            bankNode = bankLoader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void save(){
        try{
            bankLoader.save(bankNode);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Path getBankDir(){
        return bankDir;
    }

    public CommentedConfigurationNode get(){
        return bankNode;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

    }
}
