package com.github.unchama.banassist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	private static Map<String, TabExecutor> commandlist = new LinkedHashMap<String, TabExecutor>();
	private static Map<String, String> kicks = new LinkedHashMap<String, String>();

	@Override
	public void onEnable() {
		// リスナー登録
		getServer().getPluginManager().registerEvents(new BanAssistListener(), this);
		// コマンドの登録
		commandlist.put("ignore", new ignoreCommand());
		new Config(this);

		// HTTP通信でJSONデータを取得
		try {
			URL url = new URL(Config.getJsonUrl());
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
					kicks.put((String) jsonObject.get("uuid"), (String) jsonObject.get("name"));
				}
			}
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BanAssistチェックリストの読み込みに成功");
		} catch (Exception e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "BanAssistチェックリストの読み込みに失敗");
			e.printStackTrace();
		}
	}

	public static void banCheck(PlayerLoginEvent event) {
		String name = event.getPlayer().getName();
		String uuid = event.getPlayer().getUniqueId().toString();
		// UUIDがキックリストに含まれている場合
		if (kicks.containsKey(uuid)) {
			// UUIDが除外リストに含まれていない場合
			if (!Config.getUUIDs().contains(uuid)) {
				// 名前が除外リストに含まれていない場合
				if (!Config.getNames().contains(name)) {
					// キック
					event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "[ Access Denied! / Reason : Compromised Account ]\n"
							+ ChatColor.RESET + ChatColor.WHITE + "お使いのアカウントは不正アカウントの可能性がある為アクセスを拒否されました"
							);
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Compromised Account -> " + event.getPlayer().getName());
				} else {
					// 名前が除外リストに含まれている場合→UUIDの登録待ち
					Config.setUuid(uuid, name);
				}
			}
		}
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandlist.get(cmd.getName()).onCommand(sender, cmd, label, args);
	}
}
