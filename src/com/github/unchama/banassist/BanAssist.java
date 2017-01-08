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

import org.bukkit.entity.Player;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void banCheck(Player p) {
		if (kicks.contains(p.getUniqueId())) {
			p.kickPlayer("Compromised Accountのためこのサーバーには参加できません。");
		}
	}

	@Override
	public void onDisable() {
	}
}
