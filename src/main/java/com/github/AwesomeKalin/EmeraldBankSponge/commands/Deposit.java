package com.github.AwesomeKalin.EmeraldBankSponge.commands;

import com.github.AwesomeKalin.EmeraldBankSponge.ConfigurationManager;
import com.github.AwesomeKalin.EmeraldBankSponge.api.GetBankPlace;
import com.github.AwesomeKalin.EmeraldBankSponge.api.IsInt;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;

public class Deposit implements CommandExecutor {
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
        if(!args.hasAny("emeralds")) {
            ItemStack holding = p.getItemInHand(HandTypes.MAIN_HAND).get();
            ItemType holdingType = holding.getType();
            int amount = holding.getQuantity();
            if (holdingType.equals(ItemTypes.EMERALD)) {
                assert bankEmeralds != null;
                bankEmeralds.set(place, amount + bankEmeralds.get(place));
                p.setItemInHand(HandTypes.MAIN_HAND, ItemStack.builder().itemType(ItemTypes.AIR).build());
                src.sendMessage(Text.of("Emeralds added!"));
            } else if (holdingType.equals(ItemTypes.IRON_INGOT)) {
                assert bankIron != null;
                bankIron.set(place, amount + bankIron.get(place));
                p.setItemInHand(HandTypes.MAIN_HAND, ItemStack.builder().itemType(ItemTypes.AIR).build());
                src.sendMessage(Text.of("Iron added!"));
            } else {
                src.sendMessage(Text.of("You must be holding emeralds or iron, or specify an amount, please do /eb help"));
            }
            config.getConfig().getNode("bank.banks").setValue(bankNames);
            config.getConfig().getNode("bank.emeralds").setValue(bankEmeralds);
            config.getConfig().getNode("bank.iron").setValue(bankIron);
            config.saveConfig();
            config.loadConfig();
            return CommandResult.success();
        }

        if(!args.hasAny("emeralds")){
            src.sendMessage(Text.of("You must be holding emeralds or iron, or specify an amount, please do /eb help"));
            return CommandResult.success();
        }

        if(IsInt.isInt(args.<String>getOne("emeralds").get())){
            int emeralds = Integer.parseInt(args.<String>getOne("emeralds").get());
            assert bankEmeralds != null;
            bankEmeralds.set(place, emeralds + bankEmeralds.get(place));
            src.sendMessage(Text.of("Emeralds added!"));
        } else {
            src.sendMessage(Text.of("Value must be an integer! See /eb help"));
        }

        if(args.hasAny("iron")){
            if(IsInt.isInt(args.<String>getOne("iron").get())){
                int iron = Integer.parseInt(args.<String>getOne("iron").get());
                assert bankIron != null;
                bankIron.set(place, iron + bankIron.get(place));
                src.sendMessage(Text.of("Iron added!"));
            } else {
                src.sendMessage(Text.of("Value must be an integer! See /eb help"));
            }
        }

        return CommandResult.success();
    }
}
