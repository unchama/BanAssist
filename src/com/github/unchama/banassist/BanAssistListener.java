package com.github.unchama.banassist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanAssistListener implements Listener {
	@EventHandler
	public void onplayerJoinEvent(PlayerLoginEvent event) {
		BanAssist.banCheck(event);
	}
}
