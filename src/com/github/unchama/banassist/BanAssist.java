package com.github.unchama.banassist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.unchama.banassist.util.Config;

public class BanAssist extends JavaPlugin {
	public static Config config;
	public List<UUID> kicks;

	@Override
	public void onEnable() {
		// コンフィグ読み込み
		config = new Config(this);
		config.loadConfig();
//		System.out.println("debug");

		// 各packageの初期化
		// リスナー登録
		getServer().getPluginManager().registerEvents(new BanAssistListener(this), this);

		kicks = new ArrayList<UUID>();
		// HTTP通信でJSONデータを取得
		try {
			URL url = new URL(config.getJsonUrl());
			URLConnection urlCon = url.openConnection();
			// 403回避のためユーザーエージェントを登録
			urlCon.setRequestProperty("User-Agent", "BanAssist");
			InputStream in = urlCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String jstr = reader.readLine();

			// 不要な配列[]を除去、},{でsplitするために一時トークンを付与
			String[] banlist = jstr.replace("[", "").replace("]", "").replace("},{", "}},{{").split(Pattern.quote("},{"));
			// 各プレイヤーに対してチェック
			for (String p : banlist) {
				JSONObject jsonObject = (JSONObject) new JSONParser().parse(p);
				if (jsonObject.get("reason").equals("Compromised Account")) {
					kicks.add(UUID.fromString((String) jsonObject.get("uuid")));
				}
			}
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BanAssistチェックリストの読み込みに成功");
		} catch (Exception e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "BanAssistチェックリストの読み込みに失敗");
			e.printStackTrace();
		}
	}

	public void banCheck(PlayerLoginEvent event) {
		if (kicks.contains(event.getPlayer().getUniqueId())) {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER,ChatColor.YELLOW + "Compromised Account判定を受けたアカウントを用いての参加はできません");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Compromised Account -> " + event.getPlayer().getName());
		}
	}

	@Override
	public void onDisable() {
	}
}
