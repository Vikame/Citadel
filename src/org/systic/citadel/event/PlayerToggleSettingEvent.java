package org.systic.citadel.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerToggleSettingEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    public final Player player;
    public final String setting;
    public final boolean state;
    public boolean cancelled;

    public PlayerToggleSettingEvent(Player player, String setting, boolean state){
        this.player = player;
        this.setting = setting;
        this.state = state;
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
