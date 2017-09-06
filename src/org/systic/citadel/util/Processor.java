package org.systic.citadel.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Processor extends BukkitRunnable{

	private final List<String> lines;
	private int index;
	
	public Processor(List<String> lines) {
		this.lines = lines;
		this.index = 0;
	}
	
	@Override
	public void run() {
		if(lines.size() > index){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), lines.get(index++));
		}else cancel();
	}

}
