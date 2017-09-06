package org.systic.citadel.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CitadelPermissionCheckEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    public final CommandSender sender;
    public final String action;
    public boolean cancelled;

    public CitadelPermissionCheckEvent(CommandSender sender, String action){
        this.sender = sender;
        this.action = action;
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
