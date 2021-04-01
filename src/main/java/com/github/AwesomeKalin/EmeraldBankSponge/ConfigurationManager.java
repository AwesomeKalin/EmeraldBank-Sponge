package com.github.AwesomeKalin.EmeraldBankSponge;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {

    private static final ConfigurationManager instance = new ConfigurationManager();

    public static ConfigurationManager getInstance(){
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    public CommentedConfigurationNode config;

    public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader){
        this.configLoader = configLoader;

        if(!configFile.exists()){
            try{
                configFile.createNewFile();
                loadConfig();
                saveConfig();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            loadConfig();
        }
    }

    public CommentedConfigurationNode getConfig(){
        return config;
    }

    public void saveConfig(){
        try{
            configLoader.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try{
            config = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
