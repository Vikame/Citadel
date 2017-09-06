package org.systic.citadel.commands.impl;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.simpleenums.Enchant;
import org.systic.citadel.util.C;
import org.systic.citadel.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GiveCommand extends CommandBase {

    public GiveCommand() {
        super("give", "systic.give");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if (args.length <= 2) {
            sender.sendMessage(C.c("&cUsage: /" + label + " <player> <item> <amount> [metadata]"));
            return;
        }

        List<Player> targets = toPlayers(args[0]);
        int amt = toInt(args[2]);

        if(targets.isEmpty()){
            sender.sendMessage(C.c("&cCould not find any players matching the name '" + args[0] + "'."));
            return;
        }
        if(amt <= 0){
            sender.sendMessage(C.c("&cYou cannot give less than 0 of an item."));
            return;
        }

        String mat = args[1].toUpperCase();

        if(mat.equals("AIR")){
            sender.sendMessage(C.c("&6You cannot give someone air, unless you really tried!"));
            return;
        }

        int dur = 0;

        if (mat.contains(":")) {
            String[] split = mat.split(":");

            mat = split[0];
            dur = toInt(split[1]);

            if (dur < 0 || dur > 15) {
                sender.sendMessage(C.c("&c" + dur + " is not a valid item damage value."));
                return;
            }
        }

        Material material = Material.getMaterial(mat);

        if (material == null) {
            sender.sendMessage(C.c("&cNo item with the name of '" + mat + "' could be found."));
            return;
        }

        String name = Strings.upperCamelCase(material.name().replace("_", " "));

        String desc = "&6You have been given " + (amt == 1 ? Strings.startsWithVowel(name) ? "an" : "a" : amt) + " &f" + name + (amt > 1 ? "s" : "") + " ";

        ItemStack item = new ItemStack(material, amt, (short)dur);

        if(args.length >= 4){
            for(int i = 3; i < args.length; i++){
                String meta = args[i];
                String extra = "";

                if(meta.contains(":")){
                    String[] split = meta.split(":");

                    meta = split[0];
                    extra = split[1];
                }

                if(meta.equalsIgnoreCase("lore")){
                    ItemMeta m = item.getItemMeta();

                    List<String> lore = (m.hasLore() ? m.getLore() : new ArrayList<>());
                    lore.add(C.c(extra.replace("_", " ")));

                    m.setLore(lore);

                    item.setItemMeta(m);

                    if(!desc.contains("&6with custom lore")) desc += "&6with custom lore";
                    else continue;
                }

                if(meta.equalsIgnoreCase("name")){
                    ItemMeta m = item.getItemMeta();

                    m.setDisplayName(C.c(extra.replace("_", " ")));

                    item.setItemMeta(m);

                    if(!desc.contains("&6with a custom name")) desc += "&6with a custom name";
                    else continue;
                }

                Enchant enchant = Enchant.getEnchantByName(meta);

                if(enchant != null){
                    int level = toInt(extra);
                    if(level <= 0) continue;

                    desc += "&6with &f" + enchant.getSimpleName() + " &6level &f" + level;
                    item.addUnsafeEnchantment(enchant.getBukkitVersion(), level);
                }

                if(i == args.length-1) continue;
                if(i == args.length-2) desc += " &6and ";
                else desc += "&6, ";
            }
        }

        desc = desc.trim() + "&6.";

        for(Player p : targets){
            p.sendMessage(C.c(desc));

            Map<Integer, ItemStack> map = p.getInventory().addItem(item);
            if(!map.isEmpty()){
                p.getWorld().dropItemNaturally(p.getLocation(), item);
                p.sendMessage(C.c("&cThe item you have been given has been dropped onto the ground due to your inventory being full."));
            }
        }
    }

}
