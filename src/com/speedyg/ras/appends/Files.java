package com.speedyg.ras.appends;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.entities.AdoptedAnimal;
import com.speedyg.ras.entities.AdoptedType;

public class Files {

	public static List<File> getAllMobsPlayer(Player p) {
		List<File> rlist = new ArrayList<File>();
		File d = new File(BasicAnimals.getInstance().getDataFolder() + "/owned-mobs");
		if (p != null) {
			if (d.isDirectory()) {
				if (d.listFiles() != null) {
					for (File f : d.listFiles()) {
						if (f.getName().contains("&")) {
							if (f.getName().split("&")[0].equals(p.getUniqueId().toString())
									&& f.getName().split("&")[1].equals(p.getName().toString())) {
								rlist.add(f);
							}
						}
					}
				}
			}
		}
		return rlist;
	}

	public static List<File> getAllSelledMobs() {
		List<File> rlist = new ArrayList<File>();
		File d = new File(BasicAnimals.getInstance().getDataFolder() + "/owned-mobs");
		if (d.isDirectory()) {
			if (d.listFiles() != null) {
				for (File f : d.listFiles()) {
					JSONObject jo = null;
					try {
						FileReader reader = new FileReader(f);
						JSONParser oop = new JSONParser();
						jo = (JSONObject) oop.parse(reader);
						reader.close();
					} catch (Exception e) {

					}
					if ((boolean) jo.get("is-Selling") && (double) jo.get("sell-Price") > 0) {
						rlist.add(f);
					}
				}
			}
		}

		return rlist;
	}

	public static List<File> getMobsByAdoptedType(Player p, AdoptedType t) {
		List<File> rlist = new ArrayList<File>();
		File d = new File(BasicAnimals.getInstance().getDataFolder() + "/owned-mobs");
		if (t != null && p != null) {
			if (d.isDirectory()) {
				if (d.listFiles() != null) {
					for (File f : d.listFiles()) {
						if (f.getName().contains("&")) {
							if (f.getName().split("&")[0].equals(p.getUniqueId().toString())
									&& f.getName().split("&")[1].equals(p.getName().toString())) {
								try {
									FileReader reader = new FileReader(f);
									JSONParser oop = new JSONParser();
									JSONObject jo = (JSONObject) oop.parse(reader);
									reader.close();
									if (t.equals(AdoptedType.valueOf((String) jo.get("mob-Type")))) {
										rlist.add(f);
									}
								} catch (IOException | ParseException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return rlist;
	}

	public static File findMobFile(AdoptedAnimal mob) {
		File x = new File(BasicAnimals.getInstance().getDataFolder() + "/owned-mobs");
		if (mob != null) {
			if (x.isDirectory()) {
				if (x.listFiles() != null) {
					for (File f : x.listFiles()) {
						if (f.getName().contains("&")) {
							if (f.getName().split("&")[0].equals(mob.getOwnerUUID().toString())
									&& f.getName().split("&")[1].equals(mob.getOwnerName())) {
								JSONObject jo = null;
								try {
									FileReader reader = new FileReader(f);
									JSONParser oop = new JSONParser();
									jo = (JSONObject) oop.parse(reader);
									reader.close();
								} catch (IOException | ParseException e1) {
									e1.printStackTrace();
								}

								if (((String) jo.get("mob-Name")).replaceAll("&", "§").equals(mob.getCustomName())
										&& ((String) jo.get("mob-Type")).equals(mob.getAdoptType().toString())
										&& mob.getUniqueID().equals(UUID.fromString((String) jo.get("last-UUID")))) {
									return f;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static List<File> getSelledMobsByType(AdoptedType t) {
		List<File> rlist = new ArrayList<File>();
		File d = new File(BasicAnimals.getInstance().getDataFolder() + "/owned-mobs");
		if (t != null) {
			if (d.isDirectory()) {
				if (d.listFiles() != null) {
					for (File f : d.listFiles()) {
						try {
							FileReader reader = new FileReader(f);
							JSONParser oop = new JSONParser();
							JSONObject jo = (JSONObject) oop.parse(reader);
							reader.close();
							if (t.equals(AdoptedType.valueOf((String) jo.get("mob-Type")))
									&& (boolean) jo.get("is-Selling")) {
								rlist.add(f);
							}
						} catch (IOException | ParseException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		return rlist;
	}

}
