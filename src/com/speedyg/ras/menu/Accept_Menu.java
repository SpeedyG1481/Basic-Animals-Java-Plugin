package com.speedyg.ras.menu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.entities.AdoptedType;
import com.speedyg.ras.icons.Skull;

public class Accept_Menu implements Listener {

	private BasicAnimals main;
	private Player p;
	private File f;
	private Inventory inv;
	private int id;
	private AdoptedType t;

	public Accept_Menu(BasicAnimals main, Player p, File file, int id, AdoptedType t) {
		this.main = main;
		this.p = p;
		this.f = file;
		this.id = id;
		this.t = t;
		this.inv = Bukkit.createInventory(null, 18,
				main.getConfig().getString("Options.Accept-Menu.Menu-Name") != null
						? main.getConfig().getString("Options.Accept-Menu.Menu-Name").replaceAll("&", "§")
						: "§8§nAre you accept ?");
		Bukkit.getServer().getPluginManager().registerEvents(this, this.main);
	}

	public void openMenu() {
		this.inv.setItem(4, this.readAndLetAddMenu(this.f, this.id));
		this.inv.setItem(6, this.accept());
		this.inv.setItem(2, this.reject());
		this.inv.setItem(13, this.closeMenu());
		this.p.openInventory(this.inv);
	}

	private ItemStack reject() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Accept-Menu.Reject.Icon") != null
				? main.getConfig().getString("Options.Accept-Menu.Reject.Icon")
				: "e9cdb9af38cf41daa53bc8cda7665c509632d14e678f0f19f263f46e541d8a30");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Accept-Menu.Reject.Name") != null
				? main.getConfig().getString("Options.Accept-Menu.Reject.Name").replaceAll("&", "§")
				: "§cReject");
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack accept() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Accept-Menu.Accept.Icon") != null
				? main.getConfig().getString("Options.Accept-Menu.Accept.Icon")
				: "ce2a530f42726fa7a31efab8e43dadee188937cf824af88ea8e4c93a49c57294");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Accept-Menu.Accept.Name") != null
				? main.getConfig().getString("Options.Accept-Menu.Accept.Name").replaceAll("&", "§")
				: "§aAccept");
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

	@SuppressWarnings("deprecation")
	private ItemStack readAndLetAddMenu(File file, int a) {
		JSONObject jo = null;
		try {
			FileReader reader = new FileReader(file);
			JSONParser oop = new JSONParser();
			jo = (JSONObject) oop.parse(reader);
			reader.close();

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		AdoptedType t = AdoptedType.valueOf((String) jo.get("mob-Type"));
		ItemStack item = Skull.getCustomSkull(
				main.getConfig().getString("Options.Category-Menu." + t.name().toUpperCase() + ".Icon"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Accept-Menu.Load-Item.Name") != null
				? main.getConfig().getString("Options.Accept-Menu.Load-Item.Name").replaceAll("&", "§").replaceAll(
						"<id>", String.valueOf(a + 1))
				: "§aID; §b" + (a + 1));
		List<String> tlore = main.getConfig().getStringList("Options.Accept-Menu.Load-Item.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		Date date = new Date((long) jo.get("first-OwningDate"));
		for (String s : tlore)
			rlore.add(s.replaceAll("<owner>", String.valueOf(jo.get("owner-Name"))).replaceAll("&", "§")
					.replaceAll("<range>", String.valueOf(jo.get("last-FollowRange")))
					.replaceAll("<speed>", String.valueOf((double) jo.get("last-Speed")))
					.replaceAll("<date>", String.valueOf(date.toGMTString()))
					.replaceAll("<price>", String.valueOf((double) jo.get("sell-Price")))
					.replaceAll("<name>", String.valueOf((String) jo.get("mob-Name")).replaceAll("&", "§"))
					.replaceAll("<is-Selling>", String.valueOf((boolean) jo.get("is-Selling"))));
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
									.equals(this.reject().getItemMeta().getDisplayName())) {
								Buy_Menu menu = new Buy_Menu(this.main, p, t);
								menu.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.accept().getItemMeta().getDisplayName())) {
								main.changeOwner(this.f, this.p);
								p.closeInventory();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.closeMenu().getItemMeta().getDisplayName())) {
								p.closeInventory();
							}
						}
					}
				}
			}
		}
	}

}
