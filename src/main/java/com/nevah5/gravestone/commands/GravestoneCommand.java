package com.nevah5.gravestone.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GravestoneCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if(args.length != 1) return correctSyntaxError(sender);
            switch (args[0]){
                case "list":
                    sender.sendMessage(ChatColor.RED + "It works!");
                    break;
                default:
                    return correctSyntaxError(sender);
            }
        }
        return true;
    }

    private boolean correctSyntaxError(CommandSender sender){
        sender.sendMessage(ChatColor.RED + "Please use the correct Syntax. /gravestone <list/claim> [player]");
        return true;
    }
}