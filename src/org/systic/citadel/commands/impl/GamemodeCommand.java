package org.systic.citadel.commands.impl;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.Alerts;
import org.systic.citadel.util.C;
import org.systic.citadel.util.Strings;

public class GamemodeCommand extends CommandBase {

    public GamemodeCommand() {
        super("gamemode", "systic.gamemode", true, "gm", "gms", "gmc", "gma");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        GameMode gamemode = null;
        if(label.equalsIgnoreCase("gms") || label.equalsIgnoreCase(Citadel.getInstance().getName() + ":gms")){
            gamemode = GameMode.SURVIVAL;
        }else if(label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase(Citadel.getInstance().getName() + ":gmc")){
            gamemode = GameMode.CREATIVE;
        }else if(label.equalsIgnoreCase("gma") || label.equalsIgnoreCase(Citadel.getInstance().getName() + ":gma")){
            gamemode = GameMode.ADVENTURE;
        }else{

            if(args.length <= 0){
                player.sendMessage(C.c("&cUsage: /" + label + " <gamemode>"));
                return;
            }

            String gm = args[0];
            if(gm.equalsIgnoreCase("s") || gm.equalsIgnoreCase("survival") || gm.equalsIgnoreCase("0")){
                gamemode = GameMode.SURVIVAL;
            }else if(gm.equalsIgnoreCase("c") || gm.equalsIgnoreCase("creative") || gm.equalsIgnoreCase("1")){
                gamemode = GameMode.CREATIVE;
            }else if(gm.equalsIgnoreCase("a") || gm.equalsIgnoreCase("adventure") || gm.equalsIgnoreCase("2")){
                gamemode = GameMode.ADVENTURE;
            }

            if(gamemode == null){
                player.sendMessage(C.c("&c" + gm + " is not a valid gamemode."));
                return;
            }
        }

        String fixed = Strings.upperCamelCase(gamemode.name());

        if(player.getGameMode() == gamemode){
            player.sendMessage(C.c("&cYou are already in " + fixed + " mode."));
            return;
        }

        player.setGameMode(gamemode);
        player.sendMessage(C.c("&6Your gamemode has been set to &f" + fixed + "&6."));
        Alerts.alert(sender, "Set own gamemode to " + fixed);
    }

}
