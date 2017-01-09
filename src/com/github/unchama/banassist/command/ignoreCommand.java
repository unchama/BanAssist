package com.github.unchama.banassist.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.banassist.BanAssist;

public class ignoreCommand implements TabExecutor {
	private BanAssist plugin;

	public ignoreCommand(BanAssist plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			//プレイヤーからの送信は拒否
			sender.sendMessage(ChatColor.RED + "コンソール専用コマンドです。");
			return true;
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
			BanAssist.config.setIgnore(args[1]);
		} else if (args.length == 2 && args[0].equalsIgnoreCase("rem")) {
			BanAssist.config.removeIgnore(args[1]);
		} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			List<String> ign = BanAssist.config.getIgnore();
			Bukkit.getServer().getConsoleSender().sendMessage(ign.toString());
		} else if (args.length == 2 && args[0].equalsIgnoreCase("find")) {
			if(plugin.isIgnore(args[1])) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Found -> " + args[1]);
			} else {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Not Found -> " + args[1]);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "[Usage]");
			sender.sendMessage(ChatColor.RED + "/ignore add <name>");
			sender.sendMessage(ChatColor.RED + "/ignore rem <name>");
			sender.sendMessage(ChatColor.RED + "/ignore list");
			sender.sendMessage(ChatColor.RED + "/ignore find <name>");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
