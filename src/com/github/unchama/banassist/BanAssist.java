package com.github.unchama.banassist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.unchama.banassist.command.ignoreCommand;
import com.github.unchama.banassist.util.Config;

public class BanAssist extends JavaPlugin {
	private HashMap<String, TabExecutor> commandlist = new HashMap<String, TabExecutor>();
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

		// コマンドの登録
		commandlist.put("ignore", new ignoreCommand(this));

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
//			String[] banlist = {"{\"uuid\":\"58baabf2-4a5f-4906-b6ec-0af2b832688e\", \"name\":\"CrossHearts\", \"reason\":\"Compromised Account\"}"};
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
		if (kicks.contains(event.getPlayer().getUniqueId()) && !isIgnore(event.getPlayer().getName())) {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You have been banned! / Reason : Compromised Account\n"
					+ ChatColor.RESET + ChatColor.WHITE + "お使いのアカウントは不正アカウントである可能性がある為\n"
					+ "当サーバへのログインが禁止されています\n"
					+ "正規アカウントを使用している場合はお問い合わせフォームまでお知らせ下さい"
					);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Compromised Account -> " + event.getPlayer().getName());
		}
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandlist.get(cmd.getName()).onCommand(sender, cmd, label, args);
	}

	public boolean isIgnore(String name) {
		return config.getIgnore().contains(name);
	}
}
