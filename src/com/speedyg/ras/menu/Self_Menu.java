package com.speedyg.ras.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.entities.AdoptedType;
import com.speedyg.ras.icons.Skull;

public class Self_Menu implements Listener {

    private BasicAnimals main;
    private Player p;
    private Inventory inv;

    public Self_Menu(BasicAnimals main, Player p) {
        this.p = p;
        this.main = main;
        this.inv = Bukkit.createInventory(null, 54,
                this.main.getConfig().getString("Category-Menu.Name") != null
                        ? this.main.getConfig().getString("Category-Menu.Name").replaceAll("&", "§")
                        : "§8§nSelect Category");
        Bukkit.getServer().getPluginManager().registerEvents(this, this.main);
    }

    public void openMenu() {
        this.loadItemsOfMenu();
        this.p.openInventory(this.inv);
    }

    private ItemStack glass() {
        ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 15);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(" ");
        imeta.setLore(null);
        item.setItemMeta(imeta);
        return item;
    }

    private void loadItemsOfMenu() {
        this.inv.setItem(4, this.closeMenu());
        for (int i = 9; i < 18; i++) {
            this.inv.setItem(i, glass());
        }
        int x = 18;
        for (int i = 0; i < AdoptedType.values().length; i++) {
            this.inv.setItem(x + i, this.readAtConfigToItem(AdoptedType.values()[i]));
        }

        for (int i = 36; i < 45; i++) {
            this.inv.setItem(i, glass());
        }
        this.inv.setItem(49, this.closeMenu());

    }

    private ItemStack readAtConfigToItem(AdoptedType type) {
        ItemStack item = Skull.getCustomSkull(
                main.getConfig().getString("Options.Category-Menu." + type.name().toUpperCase() + ".Icon"));
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(main.getConfig().getString("Options.Category-Menu." + type.name().toUpperCase() + ".Name")
                .replaceAll("&", "§"));
        List<String> tlore = main.getConfig()
                .getStringList("Options.Category-Menu." + type.name().toUpperCase() + ".Lore");
        ArrayList<String> rlore = new ArrayList<String>(tlore.size());
        for (String s : tlore)
            rlore.add(s.replaceAll("&", "§"));
        imeta.setLore(rlore);
        item.setItemMeta(imeta);

        return item;
    }

    private ItemStack closeMenu() {
        ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Close-Button.Icon") != null
                ? main.getConfig().getString("Options.Close-Button.Icon")
                : "884e92487c6749995b79737b8a9eb4c43954797a6dd6cd9b4efce17cf475846");
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName(main.getConfig().getString("Options.Close-Button.Name") != null
                ? main.getConfig().getString("Options.Close-Button.Name").replaceAll("&", "§")
                : "§cClose Menu");
        List<String> tlore = main.getConfig().getStringList("Options.Close-Button.Lore");
        List<String> rlore = new ArrayList<String>(tlore.size());
        if (tlore != null) {
            for (String s : tlore) {
                rlore.add(s.replaceAll("&", "§"));
            }
        }
        imeta.setLore(rlore);
        item.setItemMeta(imeta);
        return item;
    }

    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getInventory() != null) {
            if (e.getInventory().equals(this.inv)) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().hasItemMeta()) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                            if (e.getCurrentItem().getItemMeta().getDisplayName()
                                    .equals(this.closeMenu().getItemMeta().getDisplayName())) {
                                p.closeInventory();
                            } else {
                                for (AdoptedType t : AdoptedType.values()) {
                                    if (e.getCurrentItem().getItemMeta().getDisplayName()
                                            .equals(this.readAtConfigToItem(t).getItemMeta().getDisplayName())) {
                                        List_Of_Mobs_Menu menu = new List_Of_Mobs_Menu(main, p, t);
                                        menu.openMenu();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
