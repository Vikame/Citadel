package org.systic.citadel.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CitadelCommandEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    public final CommandSender sender;
    public final String command;
    public final String[] arguments;
    public boolean cancelled;

    public CitadelCommandEvent(CommandSender sender, String command, String[] arguments){
        this.sender = sender;
        this.command = command;
        this.arguments = arguments;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

}
