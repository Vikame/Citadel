package org.systic.citadel.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerList implements Representable<List<String>>, Iterable<Player> {

    private final List<String> players = new ArrayList<String>();

    public void add(Player player){
        check();
        String name = player.getName();
        if(this.players.contains(name)) return;
        this.players.add(name);
    }

    public void add(String name){
        check();
        if(this.players.contains(name)) return;
        this.players.add(name);
    }

    public void clear(){
        players.clear();
    }

    public void remove(Player player){
        check();
        String name = player.getName();
        if(!this.players.contains(name)) return;
        this.players.remove(name);
    }

    public void remove(String name){
        check();
        if(!this.players.contains(name)) return;
        this.players.remove(name);
    }

    public Player get(String name){
        check();
        if(players.contains(name)) {
            Player player = Matcher.matchPlayer(name);
            if(player == null) players.remove(name);
            return player;
        }
        return null;
    }

    public Player get(int index){
        check();
        return get(players.get(index));
    }

    public int size(){
        check();
        return players.size();
    }

    public boolean contains(Player player){
        check();
        return this.players.contains(player.getName());
    }

    public boolean contains(String name){
        check();
        return this.players.contains(name);
    }

    public Iterator<Player> iterator() {
        check();
        List<Player> list = new ArrayList<Player>();
        for(String s : players){
            list.add(Matcher.matchPlayer(s));
        }
        return list.iterator();
    }

    @Override
    public List<String> represent() {
        check();
        return players;
    }

    private void check(){
        Iterator<String> it = players.iterator();
        while(it.hasNext()){
            String next = it.next();
            if(Matcher.matchPlayer(next) == null) it.remove();
        }
    }
}