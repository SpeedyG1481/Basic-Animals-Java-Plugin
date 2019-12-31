package com.speedyg.ras.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.menu.Buy_Category_Menu;
import com.speedyg.ras.messages.Messages;

public class Command_Buy_Other_Mobs implements CommandExecutor {

	protected BasicAnimals main;

	public Command_Buy_Other_Mobs(BasicAnimals BasicAnimals) {
		this.main = BasicAnimals;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (sender.hasPermission("banimals.open.sellmenu")) {
				Buy_Category_Menu menu = new Buy_Category_Menu(main, p);
				menu.openMenu();
			} else {
				p.sendMessage(Messages.not_permission);
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(Messages.just_player_command);
		}
		return false;
	}
}
