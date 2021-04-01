package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import com.github.AwesomeKalin.EmeraldBankSponge.ConfigurationManager;
import com.github.AwesomeKalin.EmeraldBankSponge.api.GetBankPlace;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;

public class Amount implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
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
        int place = GetBankPlace.getBankPlace(name);
        if (place == -1) {
            src.sendMessage(Text.of("&4Bank doesn't exist!"));
            return CommandResult.success();
        }
        Integer emeralds = bankEmeralds.get(place);
        Integer iron = bankIron.get(place);
        src.sendMessage(Text.of("&aInformation for bank, " + name + ":"));
        src.sendMessage(Text.of("&aEmeralds: " + emeralds));
        src.sendMessage(Text.of("&aIron: " + iron));
        return CommandResult.success();
    }
}
