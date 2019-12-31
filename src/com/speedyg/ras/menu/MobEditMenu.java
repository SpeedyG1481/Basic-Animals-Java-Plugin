package com.speedyg.ras.menu;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.google.common.collect.Lists;
import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.entities.AdoptedAnimal;
import com.speedyg.ras.icons.Skull;
import com.speedyg.ras.messages.Messages;
import com.speedyg.ras.nms.CustomEntities;

public class MobEditMenu implements Listener {

	private Player p;
	private AdoptedAnimal mob;
	private Inventory inv;
	private BasicAnimals main;

	public MobEditMenu(BasicAnimals main, Player p, AdoptedAnimal mob) {
		this.main = main;
		this.p = p;
		this.mob = mob;
		this.inv = Bukkit.createInventory(null, 18,
				main.getConfig().getString("Mob-Edit-Menu.Name") != null
						? main.getConfig().getString("Mob-Edit-Menu.Name").replaceAll("&", "§")
						: "§8§nMob Edit Menu");
		Bukkit.getServer().getPluginManager().registerEvents(this, main);
	}

	public void openMenu() {
		this.loadItemsOnMenu();
		this.p.openInventory(this.inv);
	}

	private void loadItemsOnMenu() {
		this.inv.setItem(13, this.closeMenu());
		this.inv.setItem(1, this.changeSpeed());
		this.inv.setItem(2, this.changeName());
		this.inv.setItem(3, this.changeFollowRange());
		this.inv.setItem(4, this.goHome());
		this.inv.setItem(5, this.informationButton());
		this.inv.setItem(6, this.changeFollowAndAutoSpawnMode());
		this.inv.setItem(7, this.changeSell());
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

	private ItemStack changeSpeed() {
		ItemStack item = Skull
				.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Speed.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Speed.Icon")
						: "b8c83024ebb1a92f3a1ed0119c2efab4c6b48b2a58847fe7c143459de7cb30a4");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Speed.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Speed.Name").replaceAll("&", "§")
				: "§bChange Speed");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Change-Speed.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("&", "§").replaceAll("<speed>",
						new DecimalFormat("##.##").format(mob.getSpeed() * 10)));
			}
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack goHome() {
		ItemStack item = Skull.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Go-Home.Icon") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Go-Home.Icon")
				: "9271809ba91b42fb4875af4ba298e5e55f45ed73721bcea85a45db92628574f");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Go-Home.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Go-Home.Name").replaceAll("&", "§")
				: "§2Send Home");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Go-Home.Lore");
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

	private ItemStack changeName() {
		ItemStack item = Skull
				.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Name.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Name.Icon")
						: "25b3f2cfa0739c4e828316f39f90b05bc1f4ed27b1e35888511f558d4675");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Name.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Name.Name").replaceAll("&", "§")
				: "§6Change Name");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Change-Name.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("<name>", mob.getCustomName().replaceAll("&", "§")).replaceAll("&", "§"));
			}
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack changeFollowRange() {
		ItemStack item = Skull
				.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Range.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Range.Icon")
						: "fc994e142d79f56727254ae4449d2e3838033b09125bfbe743e46aa7a51729dd");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Range.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Range.Name").replaceAll("&", "§")
				: "§5Change Follow Range");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Change-Follow-Range.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("&", "§").replaceAll("<range>", String.valueOf(this.mob.getFollowRange())));
			}
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack changeSell() {
		ItemStack item = Skull
				.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Sell.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Sell.Icon")
						: "47b69c9dfb61067c9484df7d03e63f17895c9cda3325c2c534a5c22358557631");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Change-Sell.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Sell.Name").replaceAll("&", "§")
				: "§3Revalue And Sell");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Change-Sell.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("&", "§").replaceAll("<is-Selling>", String.valueOf(mob.isSelling()))
						.replaceAll("<value>", String.valueOf(mob.getPrice())));
			}
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack informationButton() {
		ItemStack item = Skull
				.getCustomSkull(main.getConfig().getString("Options.Mob-Edit-Menu.Info-Button.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Info-Button.Icon")
						: "e87031c4726ddedd65b6a11d3147e6724defbb290da29cbb79da2490546cbf");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(main.getConfig().getString("Options.Mob-Edit-Menu.Info-Button.Name") != null
				? main.getConfig().getString("Options.Mob-Edit-Menu.Info-Button.Name").replaceAll("&", "§").replaceAll(
						"<name>", mob.getCustomName())
				: "§2Information of " + mob.getCustomName());
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Info-Button.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("&", "§").replaceAll("<owner>", mob.getOwnerName())
						.replaceAll("<is-FollowMode>", String.valueOf(mob.getFollow()))
						.replaceAll("<speed>", new DecimalFormat("##.##").format(mob.getSpeed() * 10))
						.replaceAll("<frange>", String.valueOf(mob.getFollowRange()))
						.replaceAll("<trange>", String.valueOf(mob.getTPRange()))
						.replaceAll("<is-Selling>", String.valueOf(mob.isSelling()))
						.replaceAll("<value>", String.valueOf(mob.getPrice())));
			}
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

	private ItemStack changeFollowAndAutoSpawnMode() {
		ItemStack item = Skull.getCustomSkull(
				main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Auto-Spawn-Mode.Icon") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Auto-Spawn-Mode.Icon")
						: "2161be2aec6cbc25ba2686a789aafbb7e47db1dcb0f4fb204010ebad09771bae");
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(
				main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Auto-Spawn-Mode.Name") != null
						? main.getConfig().getString("Options.Mob-Edit-Menu.Change-Follow-Auto-Spawn-Mode.Name")
								.replaceAll("&", "§")
						: "§9Change Modes");
		List<String> tlore = main.getConfig().getStringList("Options.Mob-Edit-Menu.Change-Follow-Auto-Spawn-Mode.Lore");
		List<String> rlore = new ArrayList<String>(tlore.size());
		if (tlore != null) {
			for (String s : tlore) {
				rlore.add(s.replaceAll("&", "§").replaceAll("<fmode>", String.valueOf(mob.getFollow())));
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
								BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
								p.closeInventory();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.changeFollowAndAutoSpawnMode().getItemMeta().getDisplayName())) {
								mob.setFollow(!mob.getFollow());
								BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.changeSpeed().getItemMeta().getDisplayName())) {
								if (e.getClick().equals(ClickType.RIGHT)) {
									mob.setSpeed(mob.getSpeed() + 0.025);
								} else if (e.getClick().equals(ClickType.LEFT)) {
									mob.setSpeed(mob.getSpeed() - 0.025);
								} else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
									mob.setSpeed(mob.getSpeed() - 0.1);
								} else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
									mob.setSpeed(mob.getSpeed() + 0.1);
								}
								BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.changeFollowRange().getItemMeta().getDisplayName())) {
								if (e.getClick().equals(ClickType.RIGHT)) {
									mob.setFollowRange(mob.getFollowRange() + 1);
								} else if (e.getClick().equals(ClickType.LEFT)) {
									mob.setFollowRange(mob.getFollowRange() - 1);
								} else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
									mob.setFollowRange(mob.getFollowRange() - 10);
								} else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
									mob.setFollowRange(mob.getFollowRange() + 10);
								}
								BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
								this.openMenu();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.changeName().getItemMeta().getDisplayName())) {
								BasicAnimals.getInstance().getFactory()
										.newMenu(p,
												Lists.newArrayList("", "", Messages.change_name_1,
														Messages.change_name_2))
										.reopenIfFail().response((player, strings) -> {
											if (strings[0] != null && strings[1] != null)
												if (strings[0].length() + strings[1].length() > 0) {
													String s = strings[0] + " " + strings[1];
													mob.setCustomName(s.replaceAll("&", "§"));
													BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
													this.openMenu();
													return true;
												}

											return false;
										}).open();
								p.closeInventory();
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.goHome().getItemMeta().getDisplayName())) {
								mob.getBukkitEntity().remove();
								if (CustomEntities.liveMobs.contains(mob))
									CustomEntities.liveMobs.remove(mob);
								p.closeInventory();
								p.sendMessage(Messages.mob_sended_home);
							} else if (e.getCurrentItem().getItemMeta().getDisplayName()
									.equals(this.changeSell().getItemMeta().getDisplayName())) {
								if (e.getClick().equals(ClickType.RIGHT)) {
									BasicAnimals.getInstance().getFactory()
											.newMenu(p,
													Lists.newArrayList("", Messages.change_price_1,
															Messages.change_price_2, Messages.change_price_3))
											.reopenIfFail().response((player, strings) -> {
												if (strings[0] != null)
													if (strings[0].length() > 0) {
														if (BasicAnimals.getInstance().isFloat(strings[0])
																|| BasicAnimals.getInstance().isInt(strings[0])) {
															mob.setSellPrice(Float.parseFloat(strings[0]));
															BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
															p.sendMessage(Messages.sell_status_updated);
															return true;
														}
													}
												return false;
											}).open();
								} else if (e.getClick().equals(ClickType.LEFT)) {
									mob.setSelling(!mob.isSelling());
									BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
									this.openMenu();
								}

							}
						}
					}
				}
			}
		}

	}

}
