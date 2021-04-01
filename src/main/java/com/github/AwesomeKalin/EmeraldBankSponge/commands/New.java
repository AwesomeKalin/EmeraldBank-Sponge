package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import com.github.AwesomeKalin.EmeraldBankSponge.ConfigurationManager;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;

public class New implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of("Must be run as player!!!"));
            return CommandResult.success();
        }
        if(!args.hasAny("name")){
            src.sendMessage(Text.of("&cRequires name, do /eb help"));
            return CommandResult.success();
        }
        String name = args.<String>getOne("name").get();
        ConfigurationManager config = ConfigurationManager.getInstance();
        List<String> bankNames = (List<String>) config.getConfig().getNode("bank.banks").getValue();
        List<Integer> bankEmeralds = (List<Integer>) config.getConfig().getNode("bank.emeralds").getValue();
        List<Integer> bankIron = (List<Integer>) config.getConfig().getNode("bank.iron").getValue();
        assert bankNames != null;
        bankNames.add(name);
        assert bankEmeralds != null;
        bankEmeralds.add(0);
        assert bankIron != null;
        bankIron.add(0);
        config.getConfig().getNode("bank.banks").setValue(bankNames);
        config.getConfig().getNode("bank.emeralds").setValue(bankEmeralds);
        config.getConfig().getNode("bank.iron").setValue(bankIron);
        config.saveConfig();
        config.loadConfig();
        src.sendMessage(Text.of("Bank created!"));
        return CommandResult.success();
    }
}
