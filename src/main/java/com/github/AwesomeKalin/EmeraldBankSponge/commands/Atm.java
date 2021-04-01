package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import com.github.AwesomeKalin.EmeraldBankSponge.ConfigurationManager;
import com.github.AwesomeKalin.EmeraldBankSponge.api.GetBankPlace;
import com.github.AwesomeKalin.EmeraldBankSponge.api.IsInt;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;

public class Atm implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of("Must be run as player!!!"));
            return CommandResult.success();
        }
        if(!args.hasAny("name")){
            src.sendMessage(Text.of("&cRequires name, do /eb help"));
            return CommandResult.success();
        }
        if(!args.hasAny("emeralds")){
            src.sendMessage(Text.of("&cRequires emerald amount, do /eb help"));
            return CommandResult.success();
        }
        Player p = (Player) src;
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
        if(!IsInt.isInt(args.<String>getOne("emeralds").get())){
            src.sendMessage(Text.of("&cRequires integer, do /eb help"));
        }
        assert bankEmeralds != null;
        bankEmeralds.set(place, bankEmeralds.get(place) - Integer.parseInt(args.<String>getOne("emeralds").get()));
        p.getInventory().offer(ItemStack.of(ItemTypes.EMERALD, Integer.parseInt(args.<String>getOne("emeralds").get())));
        config.getConfig().getNode("bank.banks").setValue(bankNames);
        config.getConfig().getNode("bank.emeralds").setValue(bankEmeralds);
        config.getConfig().getNode("bank.iron").setValue(bankIron);
        config.saveConfig();
        config.loadConfig();
        if(args.hasAny("iron")){
            if(!IsInt.isInt(args.<String>getOne("iron").get())){
                src.sendMessage(Text.of("&cRequires integer, do /eb help"));
                return CommandResult.success();
            }
            assert bankIron != null;
            bankIron.set(place, bankIron.get(place) - Integer.parseInt(args.<String>getOne("iron").get()));
            p.getInventory().offer(ItemStack.of(ItemTypes.IRON_INGOT, Integer.parseInt(args.<String>getOne("iron").get())));
            config.getConfig().getNode("bank.banks").setValue(bankNames);
            config.getConfig().getNode("bank.emeralds").setValue(bankEmeralds);
            config.getConfig().getNode("bank.iron").setValue(bankIron);
            config.saveConfig();
            config.loadConfig();
        }

        return CommandResult.success();
    }

}
