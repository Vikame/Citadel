package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.util.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageCommand extends CommandBase{

    private final List<UUID> notify = new ArrayList<>();

    public MessageCommand() {
        super("message", null, true, "msg", "m", "tell", "pm");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length <= 1){
            player.sendMessage(C.c("&cUsage: /" + label + " <player> <message>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(C.c("&cPlayer '" + args[0] + "' was not found."));
            return;
        }

        if(target == player && !notify.contains(player.getUniqueId())){
            notify.add(player.getUniqueId());

            player.sendMessage(C.c("&cPlease know that talking to yourself is allowed, however you will have to supply both sides of the conversation."));
        }

        if(!player.hasPermission("systic.message.bypass")) {
            if (!PlayerSettings.get(target).get("messages", true)) {
                player.sendMessage(C.c("&c" + target.getName() + " has disabled private messages."));
                return;
            }else if(Citadel.getInstance().messageManager.isIgnored(player, target)){
                player.sendMessage(C.c("&cYou cannot send a message to a player you have ignored."));
                return;
            }else if(Citadel.getInstance().messageManager.isIgnored(target, player)){
                player.sendMessage(C.c("&cYou cannot send a message to a player who has ignored you."));
                return;
            }
        }

        String msg = "";
        for(int i = 1; i < args.length; i++){
            msg += args[i] + " ";
        }

        msg = msg.trim();

        Citadel.getInstance().messageManager.message(player, target, msg);
    }

}
