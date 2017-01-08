package com.github.unchama.banassist.util;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.banassist.BanAssist;

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
}
