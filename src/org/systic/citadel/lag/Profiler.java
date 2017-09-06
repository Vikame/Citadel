package org.systic.citadel.lag;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.systic.citadel.Citadel;
import org.systic.citadel.Common;
import org.systic.citadel.generic.CitadelException;
import org.systic.citadel.util.C;
import org.systic.citadel.util.Debug;

import java.util.*;

public class Profiler implements Listener{

    private static final Map<String, Profiler> PROFILERS = new HashMap<>();

    private final Map<Profile, Long> start;
    public final Map<String, Profile> profiles;

    public Inventory inventory;

    public Profiler(Plugin plugin){
        if(PROFILERS.containsKey(plugin.getName().toLowerCase())) throw new CitadelException("Profiler already created for " + plugin.getName());

        profiles = new HashMap<>();
        start = new HashMap<>();

        inventory = Bukkit.createInventory(null, 9, C.c("&8Profiler"));

        Bukkit.getPluginManager().registerEvents(this, plugin);

        PROFILERS.put(plugin.getName().toLowerCase(), this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getName().equals(this.inventory.getName())) e.setCancelled(true);
    }

    public Inventory update(){
        int size = inventory.getSize();
        if(size < profiles.size()){
            while(size < profiles.size()){
                size += 9;
            }

            List<Player> reopen = new ArrayList<>();
            for(HumanEntity viewer : inventory.getViewers()){
                if(viewer instanceof Player){
                    Player p = (Player)viewer;

                    p.closeInventory();
                    reopen.add(p);
                }
            }

            inventory = Bukkit.createInventory(null, size, C.c("&8Profiler"));

            new BukkitRunnable(){
                public void run(){
                    for(Player p : reopen){
                        p.openInventory(inventory);
                    }
                }
            }.runTaskLater(Citadel.getInstance(), 1);
        }

        int index = 0;

        List<Profile> sorted = new ArrayList<>(profiles.values());

        Collections.sort(sorted, new Comparator<Profile>() {
            @Override
            public int compare(Profile o1, Profile o2) {
                return o1.icon.getId() - o2.icon.getId();
            }
        });

        for(Profile profile : sorted){
            ItemStack item = new ItemStack(profile.icon);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(C.c("&6" + profile.name));
            meta.setLore(Arrays.asList(C.c("&eTime taken: &f" + (profile.ns < 0 ? "0.00" : "" + (Common.DECIMAL_FORMAT0x00.format((profile.ns / 1000000.0)))) + "ms")));

            item.setItemMeta(meta);

            inventory.setItem(index++, item);
        }

        for(; index < inventory.getSize(); index++){
            inventory.setItem(index, null);
        }

        return inventory;
    }

    public void register(Profile profile){
        profiles.put(profile.name, profile);
    }

    public void begin(String name){
        if(profiles.containsKey(name)){
            start.put(profiles.get(name), System.nanoTime());
        }
    }

    public void end(String name){
        if(profiles.containsKey(name)){
            Profile profile = profiles.get(name);

            if(profile == null) return;
            
            if(!start.containsKey(profile)) return;

            profile.ns = System.nanoTime()-start.remove(profile);

            if(profile.ns > 25000000.0){
                Debug.sendDebugMessage("Profile '" + profile.name + "' took longer than expected: " + Common.DECIMAL_FORMAT0x00.format(profile.ns / 1000000.0) + "ms");
            }

            update();
        }
    }

    public static Profiler getProfiler(String id){
        if(PROFILERS.containsKey(id.toLowerCase()))
            return PROFILERS.get(id.toLowerCase());

        return null;
    }

}
