package com.speedyg.ras.menu;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.Lists;
import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.appends.Files;
import com.speedyg.ras.entities.AdoptedAnimal;
import com.speedyg.ras.entities.AdoptedType;
import com.speedyg.ras.icons.Skull;
import com.speedyg.ras.messages.Messages;
import com.speedyg.ras.nms.CustomEntities;

public class List_Of_Mobs_Menu implements Listener {

	private Player p;
	private BasicAnimals main;
	private AdoptedType t;
	private int get = 0;
	private int plus = 0;
	private List<File> mobs;
	private HashMap<Integer, Inventory> inv = new HashMap<Integer, Inventory>();

	public List_Of_Mobs_Menu(BasicAnimals main, Player p, AdoptedType t) {
		this.p = p;
		this.main = main;
		this.t = t;
		this.mobs = Files.getMobsByAdoptedType(p, t);
		this.plus = (this.mobs.size() / 45) + 1;
		Bukkit.getServer().getPluginManager().registerEvents(this, this.main);
		for (int i = 0; i < plus; i++) {
			this.inv.put(i, Bukkit.createInventory(null, 54,
					main.getConfig().getString("Options.Category-Menu." + t.name().toUpperCase() + ".Menu-Name") != null
							? main.getConfig()
									.getString("Options.Category-Menu." + t.name().toUpperCase() + ".Menu-Name")
									.replaceAll("&", "§")
							: "§8§nYour " + t.name().toLowerCase() + "'s"));
		}
	}

	public void openMenu() {
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
			if (sira >= this.mobs.size()) {
				break;
			}
			this.inv.get(get).setItem(a, readAndLetAddMenu(this.mobs.get(sira), a));
			sira++;
		}
		this.p.openInventory(this.inv.get(get));
	}

	@SuppressWarnings("deprecation")
	private ItemStack readAndLetAddMenu(File file, int id) {
		JSONObject jo = null;
		try {
			FileReader reader = new FileReader(file);
			JSONParser oop = new JSONParser();
			jo = (JSONObject) oop.parse(reader);
			reader.close();

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		ItemStack item = Skull.getCustomSkull(
				main.getConfig().getString("Options.Category-Menu." + t.name().toUpperCase() + ".Icon"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Liste-Mobs-Menu.Load-Item.Name") != null
				? main.getConfig().getString("Options.Liste-Mobs-Menu.Load-Item.Name").replaceAll("&", "§").replaceAll(
						"<id>", String.valueOf(id + 1))
				: "§aID; §b" + (id + 1));
		List<String> tlore = main.getConfig().getStringList("Options.Liste-Mobs-Menu.Load-Item.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		Date date = new Date((long) jo.get("first-OwningDate"));
		for (String s : tlore)
			rlore.add(s.replaceAll("&", "§").replaceAll("<range>", String.valueOf(jo.get("last-FollowRange")))
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

	@SuppressWarnings("unchecked")
	@EventHandler
	private void onInventoryClickEvent(InventoryClickEvent e) {
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
									.equals(this.goBack().getItemMeta().getDisplayName())) {
								Self_Menu menu = new Self_Menu(this.main, p);
								menu.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.nextPage().getItemMeta().getDisplayName())) {
								get++;
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.previousPage().getItemMeta().getDisplayName())) {
								get--;
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(
									this.readAndLetAddMenu(mobs.get((get * 45) + e.getSlot()), (get * 45) + e.getSlot())
											.getItemMeta().getDisplayName())) {
								File f = mobs.get((get * 45) + e.getSlot());
								if (e.getClick().equals(ClickType.LEFT)) {
									if (!CustomEntities.isHired(f)) {
										CustomEntities.hire(p, f);
									} else {
										AdoptedAnimal mob = CustomEntities.getHired(f);
										mob.getBukkitEntity().teleport(mob.getOwner());
										mob.getOwner().sendMessage(Messages.only_hired);
									}
									p.closeInventory();
								} else if (e.getClick().equals(ClickType.RIGHT)) {
									BasicAnimals.getInstance().getFactory()
											.newMenu(p,
													Lists.newArrayList(" ", Messages.change_price_1,
															Messages.change_price_2, Messages.change_price_3))
											.reopenIfFail().response((player, strings) -> {
												if (strings[0] != null)
													if (strings[0].length() > 0) {
														if (BasicAnimals.getInstance().isFloat(strings[0])
																|| BasicAnimals.getInstance().isInt(strings[0])) {
															float value = Float.parseFloat(strings[0]);
															JSONObject jo = null;
															try {
																FileReader reader = new FileReader(f);
																JSONParser oop = new JSONParser();
																jo = (JSONObject) oop.parse(reader);
																reader.close();

															} catch (IOException | ParseException ex) {
																ex.printStackTrace();
															}
															if (value > 1) {

																jo.put("is-Selling", true);
																jo.put("sell-Price", value);
																if (CustomEntities.isHired(f)) {
																	CustomEntities.getHired(f).setSelling(true);
																	CustomEntities.getHired(f).setSellPrice(value);
																}
																FileWriter writer;
																try {
																	writer = new FileWriter(f);
																	writer.write(jo.toString());
																	writer.close();
																} catch (IOException ex) {
																	ex.printStackTrace();
																}
																p.sendMessage(Messages.sell_status_updated);
															} else {
																jo.put("is-Selling", false);
																jo.put("sell-Price", 0);
																if (CustomEntities.isHired(f)) {
																	CustomEntities.getHired(f).setSelling(false);
																	CustomEntities.getHired(f).setSellPrice(0);
																}

															}

															return true;
														}
													}
												return false;
											}).open();
								}
							}
						}
					}
				}
			}
		}
	}

}
