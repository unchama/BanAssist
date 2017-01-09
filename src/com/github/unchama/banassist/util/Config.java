package com.github.unchama.banassist.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.banassist.BanAssist;

import net.md_5.bungee.api.ChatColor;

public class Config {
	private static FileConfiguration config;
	private BanAssist plugin;

	// コンストラクタ
	public Config(BanAssist plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}

	// コンフィグのロード
	public void loadConfig() {
		config = getConfig();
	}

	// コンフィグのリロード
	public void reloadConfig() {
		plugin.reloadConfig();
		config = getConfig();
	}

	// コンフィグのセーブ
	public void saveConfig() {
		plugin.saveConfig();
	}

	// plugin.ymlがない時にDefaultのファイルを生成
	public void saveDefaultConfig() {
		plugin.saveDefaultConfig();
	}

	// plugin.ymlファイルからの読み込み
	public FileConfiguration getConfig() {
		return plugin.getConfig();
	}

	public String getJsonUrl() {
		return config.getString("json");
	}

	public void setIgnore(String name) {
		// 書き込み前の最新を取得（手動編集を考慮）
		reloadConfig();
		List<String> ign = getIgnore();
		ign.add(name);
		config.set("ignore", ign);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Add Ignore -> " + name);
		saveConfig();
	}

	public void removeIgnore(String name) {
		reloadConfig();
		List<String> ign = getIgnore();
		// 削除対象が存在すれば削除
		if (ign.remove(name)) {
			config.set("ignore", ign);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Remove Ignore -> " + name);
			saveConfig();
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Not Contain -> " + name);
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getIgnore() {
		reloadConfig();
		return (List<String>) config.getList("ignore");
	}
}
