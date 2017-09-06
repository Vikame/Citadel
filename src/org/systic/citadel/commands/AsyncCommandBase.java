package org.systic.citadel.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.systic.citadel.Citadel;
import org.systic.citadel.Common;
import org.systic.citadel.event.CitadelEventHelper;
import org.systic.citadel.lag.Profile;
import org.systic.citadel.lag.Profiler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AsyncCommandBase extends Command{

    private static CommandMap map;

    public final String name;
    public String permission;
    public String[] aliases;
    public boolean playerOnly;

    private boolean registered;

    public AsyncCommandBase(String name){
        super(name);

        if(map == null){
            try{
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);

                map = (CommandMap)field.get(Bukkit.getServer());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        this.name = name;
        this.aliases = new String[0];

        Citadel.getInstance().profiler.register(new Profile("Command (/" + name + ")", Material.BOOK));

        register();
    }

    public AsyncCommandBase(String name, String permission, String... aliases){
        super(name, "", "/" + name, Arrays.asList(aliases));

        if(map == null){
            try{
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);

                map = (CommandMap)field.get(Bukkit.getServer());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        this.name = name;
        this.permission = permission;
        this.aliases = aliases;

        Citadel.getInstance().profiler.register(new Profile("Command (/" + name + ")", Material.BOOK));

        register();
    }

    public AsyncCommandBase(String name, String permission, boolean playerOnly, String... aliases){
        super(name, "", "/" + name, Arrays.asList(aliases));

        if(map == null){
            try{
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);

                map = (CommandMap)field.get(Bukkit.getServer());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
        this.playerOnly = playerOnly;

        Citadel.getInstance().profiler.register(new Profile("Command (/" + name + ")", Material.BOOK));

        register();
    }

    public void register(){
        if(registered) return;
        registered = true;

        map.register(Citadel.getInstance().getName(), this);
    }

    public void unregister(){
        if(!registered) return;
        registered = false;

        unregister(map);

        Map<String, Command> commands = null;
        try {
            Field field = map.getClass().getDeclaredField("knownCommands");
            field.setAccessible(true);

            commands = (Map<String, Command>)field.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(commands == null) return;
        commands.remove(name.toLowerCase());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(playerOnly && !(sender instanceof Player)){
            sender.sendMessage(Common.PLAYER_ONLY);
            return true;
        }

        if(permission != null && !sender.hasPermission(permission)){
            if(CitadelEventHelper.callPermissionCheckEvent(sender, label)) {
                sender.sendMessage(Common.NO_PERMISSION);
                return true;
            }
        }

        if(CitadelEventHelper.callCommandEvent(sender, name, args)) return true;

        Profiler profiler = Citadel.getInstance().profiler;

        new BukkitRunnable(){
            public void run(){
                profiler.begin("Command (/" + name + ")");
                AsyncCommandBase.this.run(sender, label, args);
                profiler.end("Command (/" + name + ")");
            }
        }.runTaskAsynchronously(Citadel.getInstance());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> ret = new ArrayList<>();

        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(args.length <= 0 || p.getName().toLowerCase().startsWith(args[args.length-1].toLowerCase())) ret.add(p.getName());
        }

        return ret;
    }

    public List<Player> toPlayers(String arg) {
        List<Player> ret = new ArrayList<>();

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (arg.equalsIgnoreCase("all") || p.getName().toLowerCase().startsWith(arg.toLowerCase())) ret.add(p);
        }

        return ret;
    }

    public int toInt(String arg){
        try{
            return Integer.parseInt(arg);
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public long toLong(String arg){
        try{
            return Long.parseLong(arg);
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public double toDouble(String arg){
        try{
            return Double.parseDouble(arg);
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public float toFloat(String arg){
        try{
            return Float.parseFloat(arg);
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public abstract void run(CommandSender sender, String label, String[] args);

}
