package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class Help implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of("Must be run as player!!!"));
            return CommandResult.success();
        }
        src.sendMessage(Text.of("&6EmeraldBank Help!"));
        src.sendMessage(Text.of("&6/eb help: Opens this menu"));
        src.sendMessage(Text.of("&6/eb new [name]: Creates a new bank"));
        src.sendMessage(Text.of("&6/eb deposit [name] [optional: emeralds] [optional: iron]: Deposit emeralds or iron to the specified bank. If no amount is specified, then the amount of emeralds/iron you are holding is deposited"));
        src.sendMessage(Text.of("&6/eb atm [name] [emeralds] [optional: iron]: Gives you the specified amount of emeralds/iron from the specified bank. If you don't want emeralds, put 0"));
        src.sendMessage(Text.of("&6/eb amount [name]: Shows the amount of emeralds and iron a bank has"));
        return CommandResult.success();
    }
}
