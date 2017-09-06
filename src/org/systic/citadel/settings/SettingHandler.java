package org.systic.citadel.settings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.systic.citadel.Citadel;
import org.systic.citadel.event.CitadelEventHelper;
import org.systic.citadel.util.C;

import java.util.Iterator;

public class SettingHandler implements Listener{

    public SettingHandler(){
        Bukkit.getPluginManager().registerEvents(this, Citadel.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(!PlayerSettings.get(p).get("global chat", true)){
            p.sendMessage(C.c("&cYou cannot speak while you have global chat disabled."));
            e.setCancelled(true);
            return;
        }

        Iterator<Player> it = e.getRecipients().iterator();
        while(it.hasNext()){
            if(!PlayerSettings.get(it.next()).get("global chat", true)){
                it.remove();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player) || e.getClickedInventory() == null || e.getCurrentItem() == null) return;

        Player p = (Player)e.getWhoClicked();
        Inventory i = e.getClickedInventory();

        if(i.getName().equalsIgnoreCase(C.c("&0Settings"))){
            e.setCancelled(true);

            PlayerSettings settings = PlayerSettings.get(p);

            ItemStack item = e.getCurrentItem();

            if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

            String setting = settings.getNameOf(item.getItemMeta().getDisplayName());

            if(setting == null) return;

            if(CitadelEventHelper.callSettingEvent(p, setting, settings.get(setting))) return;

            settings.toggle(setting);

            Inventory inv = settings.toInventory();
            if(i.getSize() != i.getSize()){
                p.closeInventory();

                new BukkitRunnable(){
                    public void run(){
                        p.openInventory(inv);
                    }
                }.runTaskLater(Citadel.getInstance(), 1);
            }else
                i.setContents(settings.getContents());
        }
    }

}
