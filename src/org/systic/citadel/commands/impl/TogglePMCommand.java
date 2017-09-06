package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.Common;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.lag.Profiler;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.util.C;

public class TogglePMCommand extends CommandBase {

    public TogglePMCommand() {
        super("togglepm", null, true, "tpm");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        if(PlayerSettings.get(player).toggle("messages")){
            player.sendMessage(C.c("&aYou have enabled private messages."));
        }else{
            player.sendMessage(C.c("&cYou have disabled private messages."));
        }
    }

}
