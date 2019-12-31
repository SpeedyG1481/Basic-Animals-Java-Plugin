package com.speedyg.ras.citem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.speedyg.ras.BasicAnimals;

public class Items {

	public static ItemStack getRentPaper() {
		ItemStack item = null;
		String icon = BasicAnimals.getInstance().getConfig().getString("Items.Paper.Material") != null
				? BasicAnimals.getInstance().getConfig().getString("Items.Paper.Material")
				: "PAPER";
		short data = (short) BasicAnimals.getInstance().getConfig().getInt("Items.Paper.Data");
		String dpname = BasicAnimals.getInstance().getConfig().getString("Items.Paper.Displayname") != null
				? BasicAnimals.getInstance().getConfig().getString("Items.Paper.Displayname")
				: "&eClick and adopt anyone mob!";
		List<String> lore = BasicAnimals.getInstance().getConfig().getStringList("Items.Paper.Lore") != null
				? BasicAnimals.getInstance().getConfig().getStringList("Items.Paper.Lore")
				: Arrays.asList("", "&7If you want to owner a mob,", "&7click anyone and see this!", "");
		if (BasicAnimals.getInstance().isInt(icon)) {
			item = new ItemStack(Material.matchMaterial(icon), 1, data);
		} else {
			item = new ItemStack(Material.valueOf(icon), 1, data);
		}
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(dpname.replaceAll("&", "§"));
		ArrayList<String> rlore = new ArrayList<String>(lore.size());
		for (String s : lore) {
			rlore.add(s.replaceAll("&", "§"));
		}
		imeta.setLore(rlore);
		item.setItemMeta(imeta);
		return item;
	}

}
