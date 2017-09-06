package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.event.CitadelEventHelper;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.util.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportCommand extends CommandBase{

    public TeleportCommand() {
        super("teleport", "systic.teleport", true, "tp");
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

        if(CitadelEventHelper.callTeleportEvent(player, target, "teleport")) return;

        player.teleport(target);
        player.sendMessage(C.c("&6You have teleported to &f" + target.getName() + "&6."));
    }

}
