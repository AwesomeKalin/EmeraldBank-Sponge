package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class NoArgs implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)){
            src.sendMessage(Text.of("Must be run as player!!!"));
            return CommandResult.success();
        }
        src.sendMessage(Text.of("&cRequires arguments, do /eb help"));
        return CommandResult.success();
    }
}
