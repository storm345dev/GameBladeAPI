package org.stormdev.gbapi.storm.UUIDAPI;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflineUUIDCallable implements Callable<OfflinePlayer> {
	private String playername;
	public OfflineUUIDCallable(String playername){
		this.playername = playername;
	}
	
	@Override
	public OfflinePlayer call() throws Exception {
		return Bukkit.getOfflinePlayer(playername);
	}
}
