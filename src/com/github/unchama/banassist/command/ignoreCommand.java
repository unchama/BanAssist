package com.github.unchama.banassist.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.banassist.util.Config;

public class ignoreCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && !sender.isOp()) {
			// 一般プレイヤーからの送信は拒否…何も出さない方が良いか
//			sender.sendMessage(ChatColor.RED + "コンソールまたはオペレータ専用コマンドです。");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
			Config.setName(args[1]);
		} else if (args.length == 2 && args[0].equalsIgnoreCase("rem")) {
			Config.removeName(args[1]);
		} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			List<String> names = Config.getNames();
			sender.sendMessage(names.toString());
		} else if (args.length == 2 && args[0].equalsIgnoreCase("find")) {
			if (Config.getNames().contains(args[1])) {
				sender.sendMessage(ChatColor.YELLOW + "Found -> " + args[1]);
			} else {
				sender.sendMessage(ChatColor.YELLOW + "Not Found -> " + args[1]);
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			Config.reloadConfig();
			sender.sendMessage(ChatColor.YELLOW + "Reloaded");
		} else {
			sender.sendMessage(ChatColor.RED + "[Usage]");
			sender.sendMessage(ChatColor.RED + "  /compignoreコマンドにより未確定除外リストにプレイヤー名を登録します。");
			sender.sendMessage(ChatColor.RED + "  以降のプレイヤーのログインにより該当するUUIDを登録し、除外を確定します。");
			sender.sendMessage(ChatColor.RED + "  除外確定後の削除コマンドは用意していません。");
			sender.sendMessage(ChatColor.RED + "/compignore add <name>");
			sender.sendMessage(ChatColor.RED + "  未確定除外リストにプレイヤーを追加します。");
			sender.sendMessage(ChatColor.RED + "/compignore rem <name>");
			sender.sendMessage(ChatColor.RED + "  未確定除外リストからプレイヤーを削除します。");
			sender.sendMessage(ChatColor.RED + "  誤登録の削除が目的のため、該当プレイヤーログイン後は削除出来ません。");
			sender.sendMessage(ChatColor.RED + "/compignore list");
			sender.sendMessage(ChatColor.RED + "  未確定除外リストから未ログインのプレイヤー一覧を取得します。");
			sender.sendMessage(ChatColor.RED + "/compignore find <name>");
			sender.sendMessage(ChatColor.RED + "  未確定除外リストから未ログインのプレイヤー名を検索します。");
			sender.sendMessage(ChatColor.RED + "/compignore reload");
			sender.sendMessage(ChatColor.RED + "  除外リストをリロードします。手動でconfig.ymlを編集した後は必ず実行してください。");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
