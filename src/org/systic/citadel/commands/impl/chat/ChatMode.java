package org.systic.citadel.commands.impl.chat;

import org.bukkit.entity.Player;
import org.systic.citadel.util.PlayerList;

import java.util.ArrayList;
import java.util.List;

public abstract class ChatMode {

    private static final List<ChatMode> modes = new ArrayList<>();

    private String name;
    public PlayerList in;

    public ChatMode(String name){
        if(getModeByName(name) != null){
            try {
                throw new InstantiationException("Cannot create duplicate chat mode.");
            }catch(Exception ignored){}
        }
        this.name = name;
        this.in = new PlayerList();

        modes.add(this);
    }

    public final String getName(){
        return this.name;
    }

    public final boolean isIn(Player player){
        return in.contains(player);
    }

    public abstract void chat(String player, String message);

    public static ChatMode getModeByName(String name){
        for(ChatMode mode : modes){
            if(mode.getName().equalsIgnoreCase(name)) return mode;
        }
        return null;
    }

    public static ChatMode getModeByPlayer(Player player){
        for(ChatMode mode : modes){
            if(mode.isIn(player)) return mode;
        }
        return null;
    }

    public void setChatMode(Player player){
        for(ChatMode mode : modes){
            if(mode.isIn(player)) mode.in.remove(player);
        }
        in.add(player);
    }

    public static void setChatMode(Player player, ChatMode mode){
        if(mode == null){
            for(ChatMode m : modes){
                if(m.isIn(player)) m.in.remove(player);
            }
        }else {
            mode.setChatMode(player);
        }
    }

}