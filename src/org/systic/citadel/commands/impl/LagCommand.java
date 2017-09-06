package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.systic.citadel.Common;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

import net.minecraft.server.v1_7_R4.MinecraftServer;

public class LagCommand extends CommandBase {

	public LagCommand() {
		super("lag", null, false, "downtownchicagoserverhostedinavan");
	}

	@Override
	public void run(CommandSender sender, String label, String[] args) {
		double tps = MinecraftServer.getServer().recentTps[0];

		String color = (tps >= 18 ? C.c("&a") + "" : tps >= 15 ? C.c("&e") + "" : C.c("&c") + "");

		String str = "";
		for (double d = 0; d < 20; d += 1) {
			str += (tps > d ? color : "&7") + "|";
		}

		sender.sendMessage(C.c("&6Server performance: " + color + (tps > 20 ? "*" : "") + Common.DECIMAL_FORMAT0x00.format(tps) + "&6/20.00 [" + str + "&6]"));

	}
}
