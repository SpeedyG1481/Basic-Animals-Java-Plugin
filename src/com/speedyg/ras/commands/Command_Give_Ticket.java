package com.speedyg.ras.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.citem.Items;
import com.speedyg.ras.messages.Messages;

public class Command_Give_Ticket implements CommandExecutor {

	BasicAnimals main;

	public Command_Give_Ticket(BasicAnimals basicAnimals) {
		this.main = basicAnimals;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("banimals.give.ticket")) {
			if (args.length == 3) {
				if (args[0].equals("give") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("gonder")
						|| args[0].equalsIgnoreCase("send")) {
					String pname = args[1];
					String amound = args[2];
					if (Bukkit.getPlayer(pname) != null) {
						if (main.isInt(amound)) {
							Player p = Bukkit.getPlayer(pname);
							int amount = Integer.parseInt(amound);
							ItemStack item = Items.getRentPaper();
							item.setAmount(amount);
							p.getInventory().addItem(item);
							sender.sendMessage(Messages.send_success);
						} else {
							sender.sendMessage(Messages.just_number_pls);
						}
					} else {
						sender.sendMessage(Messages.user_is_not_online);
					}
				} else {
					sender.sendMessage(Messages.wrong_usage);
				}
			} else {
				sender.sendMessage(Messages.wrong_usage);
			}
		} else {
			sender.sendMessage(Messages.not_permission);
		}
		return false;
	}

}
