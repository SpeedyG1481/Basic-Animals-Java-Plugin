package com.speedyg.ras.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.messages.Messages;

public class Command_Admin implements CommandExecutor {

	BasicAnimals main;

	public Command_Admin(BasicAnimals main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("banimals.admin")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("yenile")) {
					main.reloadConfig();
					sender.sendMessage(Messages.reload_suceess);
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
