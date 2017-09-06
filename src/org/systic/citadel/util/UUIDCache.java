package org.systic.citadel.util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.systic.citadel.Citadel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDCache extends Cache<String, UUID> {

    private static UUIDCache instance;

    private UUIDCache(){ instance = this; }

    public static UUIDCache getInstance(){
        return (instance == null ? new UUIDCache() : instance);
    }

    private Map<String, UUID> data = new HashMap<>();
    private File file;

    public Map<String, UUID> getCachedData() {
        return data;
    }

    public boolean contains(Player p){
        return contains(p.getName());
    }

    public void add(Player p){
        if(data.containsValue(p.getUniqueId()) && !data.containsKey(p.getName())){
            String key = "";
            for(Map.Entry<String, UUID> entry : data.entrySet()){
                if(entry.getValue().equals(p.getUniqueId())){
                    key = entry.getKey();
                    break;
                }
            }
            if(!key.equals("")){
                data.remove(key);
            }
        }
        add(p.getName(), p.getUniqueId());
    }

    public void remove(Player p){
        remove(p.getName());
    }

    public UUID get(String name){
        UUID uuid = data.get(name);

        if(uuid == null) {
            new BukkitRunnable() {
                public void run() {
                    try {
                        UUID fetch = UUIDFetcher.getUUIDOf(name);

                        if(fetch != null) UUIDCache.getInstance().add(name, fetch);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(Citadel.getInstance());

            uuid = data.get(name);
        }

        return uuid;
    }

    public String get(UUID uuid){
        for(Map.Entry<String, UUID> entry : data.entrySet()){
            if(entry.getValue().equals(uuid)) return entry.getKey();
        }
        return null;
    }

    public void setFile(File file){
        this.file = file;
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException ignored){}
        }
        load();
    }

    public File getFile(){
        return this.file;
    }

    public void save() {
        try{
            BufferedWriter writer = new BufferedWriter(new PrintWriter(file));

            for(Map.Entry<String, UUID> entry : data.entrySet()){
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void load() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while((line = reader.readLine()) != null){
                if(line.contains("=")){
                    String[] split = line.split("=");
                    data.put(split[0], UUID.fromString(split[1]));
                }
            }
            
            reader.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}