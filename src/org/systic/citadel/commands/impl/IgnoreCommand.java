package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

public class IgnoreCommand extends CommandBase{

    public IgnoreCommand() {
        super("ignore", null, true);
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length <= 0){
            player.sendMessage(C.c("&cUsage: /" + label + " <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(C.c("&cPlayer '" + args[0] + "' was not found."));
            return;
        }

        if(target == player){
            player.sendMessage(C.c("&cYou cannot ignore yourself."));
            return;
        }

        if(target.hasPermission("systic.message.bypass")) {
            player.sendMessage(C.c("&cYou cannot ignore a staff member."));
            return;
        }

        if(Citadel.getInstance().messageManager.isIgnored(player, target)){
            Citadel.getInstance().messageManager.unignore(player, target);

            player.sendMessage(C.c("&6You have un-ignored &f" + target.getName() + "&6."));
        }else {
            Citadel.getInstance().messageManager.ignore(player, target);

            player.sendMessage(C.c("&6You have ignored &f" + target.getName() + "&6."));
        }
    }

}
