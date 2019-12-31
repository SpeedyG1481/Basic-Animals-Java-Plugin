package com.speedyg.ras.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.menu.Self_Menu;
import com.speedyg.ras.messages.Messages;

public class Command_Open_My_Mobs implements CommandExecutor {

	protected BasicAnimals main;

	public Command_Open_My_Mobs(BasicAnimals BasicAnimals) {
		this.main = BasicAnimals;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (sender.hasPermission("banimals.open.selfmenu")) {
				Self_Menu menu = new Self_Menu(main, p);
				menu.openMenu();
			} else {
				p.sendMessage(Messages.not_permission);
			}
		}else {
			Bukkit.getConsoleSender().sendMessage(Messages.just_player_command);
		}
		return false;
	}

}
