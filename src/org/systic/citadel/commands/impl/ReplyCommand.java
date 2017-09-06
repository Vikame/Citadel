package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.util.C;

public class ReplyCommand extends CommandBase{

    public ReplyCommand() {
        super("reply", null, true, "r");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length <= 0){
            player.sendMessage(C.c("&cUsage: /" + label + " <message>"));
            return;
        }

        Player last = Citadel.getInstance().messageManager.getLastMessenger(player);

        if(last == null){
            player.sendMessage(C.c("&cYou have nobody to reply to."));
            return;
        }

        if(!player.hasPermission("systic.message.bypass")) {
            if (!PlayerSettings.get(last).get("messages", true)) {
                player.sendMessage(C.c("&c" + last.getName() + " has disabled private messages."));
                return;
            }else if(Citadel.getInstance().messageManager.isIgnored(player, last)){
                player.sendMessage(C.c("&cYou cannot send a message to a player you have ignored."));
                return;
            }else if(Citadel.getInstance().messageManager.isIgnored(last, player)){
                player.sendMessage(C.c("&cYou cannot send a message to a player who has ignored you."));
                return;
            }
        }

        String msg = "";
        for(String s : args) msg += s + " ";

        msg = msg.trim();

        Citadel.getInstance().messageManager.message(player, last, msg);
    }

}
