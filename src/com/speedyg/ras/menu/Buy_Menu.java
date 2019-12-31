package com.speedyg.ras.menu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.speedyg.ras.appends.Files;
import com.speedyg.ras.entities.AdoptedType;
import com.speedyg.ras.icons.Skull;

public class Buy_Menu implements Listener {

	private Player p;
	private int get = 0;
	private int plus;
	private List<File> selledMobs;
	private HashMap<Integer, Inventory> inv = new HashMap<Integer, Inventory>();
	private BasicAnimals main;
	private AdoptedType t;

	public Buy_Menu(BasicAnimals main, Player p, AdoptedType t) {
		this.main = main;
		this.t = t;
		this.p = p;
		this.selledMobs = Files.getSelledMobsByType(t);
		this.plus = (this.selledMobs.size() / 45) + 1;
		for (int i = 0; i < plus; i++) {
			this.inv.put(i,
					Bukkit.createInventory(null, 54,
							main.getConfig().getString("Options.List-Buy-Menu.Menu-Name") != null
									? main.getConfig().getString("Options.List-Buy-Menu.Menu-Name").replaceAll("&", "§")
											.replaceAll("<p>", String.valueOf(i + 1))
									: "§8§nBuy BM §m-§r§8§n Page " + (i + 1)));
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this.main);
	}

	public void openMenu() {
		this.loadItemsOfMenu();
		this.p.openInventory(this.inv.get(get));
	}

	private void loadItemsOfMenu() {
		if (get != 0)
			this.inv.get(get).setItem(45, previousPage());
		else
			this.inv.get(get).setItem(45, goBack());

		if (get < this.plus - 1) {
			this.inv.get(get).setItem(53, nextPage());
		} else {
			this.inv.get(get).setItem(53, closeMenu());
		}
		int sira = 45 * get;
		for (int a = 0; a < this.inv.get(get).getSize() - 9; a++) {
			if (sira >= this.selledMobs.size()) {
				break;
			}
			this.inv.get(get).setItem(a, readAndLetAddMenu(this.selledMobs.get(sira), sira));
			sira++;
		}
		this.p.openInventory(this.inv.get(get));
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
		imeta.setDisplayName(main.getConfig().getString("Options.List-Buy-Menu.Load-Item.Name") != null
				? main.getConfig().getString("Options.List-Buy-Menu.Load-Item.Name").replaceAll("&", "§").replaceAll(
						"<id>", String.valueOf(a + 1))
				: "§aID; §b" + (a + 1));
		List<String> tlore = main.getConfig().getStringList("Options.List-Buy-Menu.Load-Item.Lore");
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

	private ItemStack previousPage() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Prev-Page.Icon") != null
				? main.getConfig().getString("Options.Prev-Page.Icon")
				: "6e8c3ce2aee6cf2faade7db37bbae73a36627ac1473fef75b410a0af97659f");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Prev-Page.Name") != null
				? main.getConfig().getString("Options.Prev-Page.Name").replaceAll("&", "§")
				: "§cPrevious Page");
		List<String> tlore = main.getConfig().getStringList("Options.Prev-Page.Lore");
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

	private ItemStack nextPage() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Next-Page.Icon") != null
				? main.getConfig().getString("Options.Next-Page.Icon")
				: "6e8cd53664d9307b6869b9abbae2b7737ab762bb18bb34f31c5ca8f3edb63b6");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Next-Page.Name") != null
				? main.getConfig().getString("Options.Next-Page.Name").replaceAll("&", "§")
				: "§aNext Page");
		List<String> tlore = main.getConfig().getStringList("Options.Next-Page.Lore");
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

	private ItemStack goBack() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Go-Back.Icon") != null
				? main.getConfig().getString("Options.Go-Back.Icon")
				: "4c93259c91647ad3af5f5ebf511b81a91312e8a2be5e55fc7897cde7c5efa1");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Go-Back.Name") != null
				? main.getConfig().getString("Options.Go-Back.Name").replaceAll("&", "§")
				: "§cGo Back");
		List<String> tlore = main.getConfig().getStringList("Options.Go-Back.Lore");
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
	private void clickEvent(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().equals(this.inv.get(get))) {
				e.setCancelled(true);
				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().hasItemMeta()) {
						if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.closeMenu().getItemMeta().getDisplayName())) {
								p.closeInventory();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.previousPage().getItemMeta().getDisplayName())) {
								get--;
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.nextPage().getItemMeta().getDisplayName())) {
								get++;
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.goBack().getItemMeta().getDisplayName())) {
								Buy_Category_Menu menu = new Buy_Category_Menu(main, p);
								menu.openMenu();
							} else {
								if (e.getCurrentItem().getItemMeta().getDisplayName()
										.equals(this.readAndLetAddMenu(this.selledMobs.get((get * 45) + e.getSlot()),
												(get * 45) + e.getSlot()).getItemMeta().getDisplayName())) {
									Accept_Menu menu = new Accept_Menu(main, p,
											this.selledMobs.get((get * 45) + e.getSlot()), (get * 45) + e.getSlot(), t);
									menu.openMenu();
								}
							}
						}
					}
				}

			}
		}
	}

}
