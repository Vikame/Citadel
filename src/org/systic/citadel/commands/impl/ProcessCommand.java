package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.Alerts;
import org.systic.citadel.util.C;
import org.systic.citadel.util.FileUtils;
import org.systic.citadel.util.Processor;

import java.io.File;
import java.util.List;

public class ProcessCommand extends CommandBase {

    public ProcessCommand() {
        super("process", "systic.process", false);
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(C.c("&cUsage: /process <path> [delay]"));
            return;
        }
    	
        File file = new File(Citadel.getInstance().getDataFolder(), args[0]);
        if(!file.exists()){
        	sender.sendMessage(C.c("&cCould not find '" + args[0] + "'."));
        	return;
        }
        
        int tickDelay = 5;
        if(args.length > 1){
        	try{
        		tickDelay = Integer.parseInt(args[1]);
        	}catch(NumberFormatException e){
        		sender.sendMessage(C.c("&c" + args[1] + " is not a valid delay."));
        		return;
        	}
        }
        if(tickDelay <= 2){
        	sender.sendMessage(C.c("&cDefaulting tick delay to 5 due to an invalid tick delay (" + tickDelay + ")."));
        	tickDelay = 5;
        }
        
        sender.sendMessage(C.c("&aProcessing all commands in file '" + file.getName() + "'."));
        new Processor(FileUtils.read(file)).runTaskTimer(Citadel.getInstance(), tickDelay, tickDelay);
        Alerts.alert(sender, "Processed file '" + file.getName() + "'");
    }

}
