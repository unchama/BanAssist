package com.github.unchama.banassist.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.banassist.BanAssist;

import net.md_5.bungee.api.ChatColor;

public class Config {
	private static FileConfiguration config;
	private static BanAssist plugin;
	private static String url;
	private static List<String> names = new ArrayList<String>();
	private static List<String> uuids = new ArrayList<String>();

	// コンストラクタ
	public Config(BanAssist plugin) {
		Config.plugin = plugin;
		plugin.saveDefaultConfig();
		loadConfig();
	}

	// コンフィグのリロード
	public static void reloadConfig() {
		plugin.reloadConfig();
		loadConfig();
	}

	// 読み込み処理
	private static void loadConfig() {
		config = plugin.getConfig();
		url = config.getString("json");
		names = config.getStringList("names");
		uuids = config.getStringList("uuids");
	}

	// コンフィグのセーブ
	public static void saveConfig() {
		plugin.saveConfig();
	}

	public static String getJsonUrl() {
		return url;
	}

	public static List<String> getNames() {
		return names;
	}

	public static List<String> getUUIDs() {
		return uuids;
	}

	// 名前を登録
	public static void setName(String name) {
		names.add(name);
		config.set("names", names);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Add Ignore Name -> " + name);
		saveConfig();
	}

	// 名前の登録を削除
	public static void removeName(String name) {
		names.remove(name);
		config.set("names", names);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Remove Ignore Name -> " + name);
		saveConfig();
	}

	// UUIDを登録(名前を削除)
	public static void setUuid(String uuid, String name) {
		uuids.add(uuid);
		config.set("uuids", uuids);
		names.remove(name);
		config.set("names", names);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Add Ignore UUID -> " + uuid);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Remove Ignore Name -> " + name);
		saveConfig();
	}
}
