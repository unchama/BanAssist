package com.github.unchama.banassist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanAssistListener implements Listener {
	private BanAssist parent;

	public BanAssistListener(BanAssist parent) {
		this.parent = parent;
	}

	@EventHandler
	public void onplayerJoinEvent(PlayerLoginEvent event) {
		parent.banCheck(event.getPlayer());
	}
}
