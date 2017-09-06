package org.systic.citadel.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CitadelTeleportEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    public final Player player, target;
    public final String command;
    public boolean cancelled;

    public CitadelTeleportEvent(Player player, Player target, String command){
        this.player = player;
        this.target = target;
        this.command = command;
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
