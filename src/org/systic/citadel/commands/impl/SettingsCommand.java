package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.util.C;

public class SettingsCommand extends CommandBase {

    public SettingsCommand() {
        super("settings", "systic.settings", true, "setting", "preferences", "prefs");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        player.openInventory(PlayerSettings.get(player).toInventory());
    }

}
