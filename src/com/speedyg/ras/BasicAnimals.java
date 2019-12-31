package com.speedyg.ras;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAnimals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.speedyg.ras.commands.Command_Admin;
import com.speedyg.ras.commands.Command_Buy_Other_Mobs;
import com.speedyg.ras.commands.Command_Give_Ticket;
import com.speedyg.ras.commands.Command_Open_My_Mobs;
import com.speedyg.ras.entities.AdoptedAnimal;
import com.speedyg.ras.events.Events;
import com.speedyg.ras.menu.SignMenuFactory;
import com.speedyg.ras.messages.Messages;
import com.speedyg.ras.nms.CustomEntities;

import de.NeonnBukkit.CoinsAPI.API.CoinsAPI;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.EntityAnimal;

public class BasicAnimals extends JavaPlugin {

	private static BasicAnimals instance;
	private SignMenuFactory signMenuFactory;
	private Econ_API api;
	private Economy eco_api;
	private File messageFile;
	private FileConfiguration mOptions;

	public BasicAnimals() {
		instance = this;
	}

	public static BasicAnimals getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		if (!this.lisansKontrol()) {
			Bukkit.getConsoleSender().sendMessage("§6§lWarning!§e Not found licence! Please buy this plugin!");
			Bukkit.getConsoleSender().sendMessage("§eIf you have licence but you receive this error,");
			Bukkit.getConsoleSender().sendMessage("§eyou can send a message to this address. §4Discord: Yusuf#7761");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.saveResource("config.yml", false);
		api = Econ_API.valueOf(this.getConfig().getString("ECON-API"));
		if (!setupEconomy()) {
			Bukkit.getConsoleSender()
					.sendMessage("§8[§bBasicMobs§8] §cNot found economy provider please check plugins and config.yml!");
			Bukkit.getConsoleSender().sendMessage("§8[§bBasicMobs§8] §cPlugin disabled!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		} else {
			int size = 0;
			File xFile = new File(getDataFolder() + "/owned-mobs");
			if (xFile.isDirectory()) {
				if (xFile.listFiles() != null) {
					size = xFile.listFiles().length;
				}
			}
			Bukkit.getConsoleSender().sendMessage("§8[§bBasicMobs§8] §aPlugin activated!");
			Bukkit.getConsoleSender().sendMessage("§8[§bBasicMobs§8] §b" + size + "§a mob's loaded.");
		}
		makeMessageFile();
		Events events = new Events();
		events.registerEvents();
		CustomEntities.registerEntities();
		signMenuFactory = new SignMenuFactory(this);
		this.getCommand("basicmobs").setExecutor(new Command_Open_My_Mobs(this));
		this.getCommand("basicmobssell").setExecutor(new Command_Buy_Other_Mobs(this));
		this.getCommand("baticket").setExecutor(new Command_Give_Ticket(this));
		this.getCommand("bmadmin").setExecutor(new Command_Admin(this));
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			p.closeInventory();
	}

	private boolean lisansKontrol() {
		Lisans lisans = null;
		try {
			lisans = new Lisans(InetAddress.getLocalHost().getHostAddress(), this.getDescription().getName());
			return lisans.getControl();
		} catch (IOException e) {
			return false;
		}
	}

	private boolean setupEconomy() {
		this.api = Econ_API.valueOf(this.getConfig().getString("ECON-API"));
		if (api != null) {
			switch (api) {
			case COINSAPI:
				if (getServer().getPluginManager().getPlugin("CoinsAPINB") != null) {
					return true;
				}
				return false;
			case VAULT:
				if (getServer().getPluginManager().getPlugin("Vault") == null) {
					return false;
				}
				RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
						.getRegistration(Economy.class);
				if (rsp == null) {
					return false;
				}
				eco_api = (Economy) rsp.getProvider();
				return eco_api != null;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public void onDisable() {
		CustomEntities.removeAllEntities();
		CustomEntities.liveMobs.clear();
		CustomEntities.liveMobs = null;
	}

	public boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isFloat(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void saveEntity(Player owner, AdoptedAnimal mob) {
		File xFile = new File(this.getDataFolder() + "/owned-mobs",
				mob.getOwnerUUID().toString() + "&" + mob.getOwnerName() + "&" + mob.getFirstSpawnDate() + ".json");

		JSONObject json = new JSONObject();
		json.put("owner-UUID", owner.getUniqueId().toString());
		json.put("owner-Name", owner.getName());
		json.put("mob-Name", mob.getCustomName().replaceAll("§", "&"));
		json.put("mob-Type", mob.getAdoptType().toString());
		json.put("first-OwningDate", mob.getFirstSpawnDate());
		json.put("is-Selling", mob.isSelling());
		json.put("sell-Price", mob.getPrice());
		json.put("last-Speed", mob.getSpeed());
		json.put("last-FollowRange", mob.getFollowRange());
		json.put("last-UUID", mob.getUniqueID().toString());

		if (!xFile.exists())
			try {
				xFile.getParentFile().mkdirs();
				xFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		FileWriter writer;
		try {
			writer = new FileWriter(xFile);
			writer.write(json.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isAdopted(Entity e) {
		if ((e instanceof CraftAnimals)) {
			EntityAnimal c = ((CraftAnimals) e).getHandle();
			if (c.getClass().getName().contains("Adopted")) {
				return true;
			}
		}
		return false;
	}

	public void removeEntity(Player owner, AdoptedAnimal mob) {
		File xFile = new File(getDataFolder() + "/owned-mobs");
		if (xFile.isDirectory()) {
			if (xFile.listFiles() != null) {
				for (File f : xFile.listFiles()) {
					if (f.getName().contains("&")) {
						if (f.getName().split("&")[1].equals(owner.getName())
								&& f.getName().split("&")[0].equals(owner.getUniqueId().toString())) {
							try {
								FileReader reader = new FileReader(f);
								JSONParser oop = new JSONParser();
								JSONObject jo = (JSONObject) oop.parse(reader);
								reader.close();
								if (((String) jo.get("mob-Name")).replaceAll("&", "§").equals(mob.getCustomName())
										&& ((String) jo.get("mob-Type")).equals(mob.getAdoptType().toString())
										&& mob.getUniqueID().equals(UUID.fromString((String) jo.get("last-UUID")))) {
									if (f.delete()) {
										if (CustomEntities.liveMobs.contains(mob))
											CustomEntities.liveMobs.remove(mob);
										mob.getBukkitEntity().remove();
										return;
									}
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

	@SuppressWarnings("deprecation")
	public void addMoneyPlayer(UUID p, String pname, double value) {
		switch (this.api) {
		case COINSAPI:
			CoinsAPI.addCoins(p.toString(), (int) value);
			break;
		case VAULT:
			eco_api.depositPlayer(pname, value);
			break;
		default:
			break;

		}
	}

	@SuppressWarnings("deprecation")
	public double getMoneyPlayer(UUID p, String pname) {
		switch (this.api) {
		case COINSAPI:
			return CoinsAPI.getCoins(p.toString());
		case VAULT:
			return eco_api.getBalance(pname);
		default:
			break;
		}

		return 0;
	}

	@SuppressWarnings("deprecation")
	public void removeMoneyPlayer(UUID p, String pname, double value) {
		switch (this.api) {
		case COINSAPI:
			CoinsAPI.removeCoins(p.toString(), (int) value);
			break;
		case VAULT:
			eco_api.withdrawPlayer(pname, value);
			break;
		default:
			break;

		}
	}

	@SuppressWarnings("unchecked")
	public void changeOwner(File file, Player newOwner) {
		if (file.exists()) {
			JSONObject jo = null;
			try {
				FileReader reader = new FileReader(file);
				JSONParser oop = new JSONParser();
				jo = (JSONObject) oop.parse(reader);
				reader.close();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
			if (!newOwner.getName().equals(jo.get("owner-Name"))
					&& !newOwner.getUniqueId().toString().equals(jo.get("owner-UUID"))) {
				if (this.getMoneyPlayer(newOwner.getUniqueId(), newOwner.getName()) >= (double) jo.get("sell-Price")) {
					this.addMoneyPlayer(UUID.fromString((String) jo.get("owner-UUID")), (String) jo.get("owner-Name"),
							(double) jo.get("sell-Price"));
					this.removeMoneyPlayer(newOwner.getUniqueId(), newOwner.getName(), (double) jo.get("sell-Price"));
					if (Bukkit.getPlayer((String) jo.get("owner-Name")) != null) {
						Bukkit.getPlayer((String) jo.get("owner-Name")).closeInventory();
					}
					newOwner.closeInventory();
					if (CustomEntities.isHired(file)) {
						AdoptedAnimal a = CustomEntities.getHired(file);
						a.getBukkitEntity().remove();
						if (CustomEntities.liveMobs.contains(a))
							CustomEntities.liveMobs.remove(a);
					}
					File newFile = new File(this.getDataFolder() + "/owned-mobs",
							newOwner.getUniqueId() + "&" + newOwner.getName() + "&" + file.getName().split("&")[2]);
					file.renameTo(newFile);
					jo.put("owner-UUID", newOwner.getUniqueId().toString());
					jo.put("owner-Name", newOwner.getName());
					jo.put("is-Selling", false);
					jo.put("sell-Price", (float) 0.0F);

					try {
						FileWriter writer = new FileWriter(newFile);
						writer.write(jo.toString());
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					newOwner.sendMessage(Messages.change_owner_successfull);
				} else {
					newOwner.sendMessage(Messages.error_money_not_enought);
				}
			} else {
				newOwner.sendMessage(Messages.only_your_mob);
			}
		} else {
			newOwner.sendMessage(Messages.just_sold_out);
		}

	}

	public SignMenuFactory getFactory() {
		return this.signMenuFactory;
	}

	public File getOptionsFile() {
		return this.messageFile;
	}

	public FileConfiguration getOptions() {
		return this.mOptions;
	}

	private void makeMessageFile() {
		this.messageFile = new File(getDataFolder(),
				"/locale/messages_" + this.getConfig().getString("Locale") + ".yml");
		if (!this.messageFile.exists()) {
			this.messageFile.getParentFile().mkdirs();
			try {
				messageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.mOptions = new YamlConfiguration();
		try {
			this.mOptions.load(this.messageFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Messages.loadMessages();
	}
}
