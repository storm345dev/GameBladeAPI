package org.stormdev.gbapi.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.stormdev.gbapi.servers.ServerInfo;
import org.stormdev.gbapi.storm.tokens.Tokens;

public interface GameBladeAPI {
	/**
	 * Retrieve the version number of the API
	 * 
	 * @return The API version number as a double
	 */
	public double getAPIVersionNumber();
	
	/**
	 * Get the handler for managing player's tokens
	 * 
	 * @return The API for handling player tokens
	 */
	public Tokens getTokenHandler();
	
	/**
	 * Get the server info the current server
	 * @return The ServerInfo for the current server
	 */
	public ServerInfo getCurrentServerInfo();
	
	/**
	 * Shows the player the server selector to change servers
	 * @param player The player to show it to
	 */
	public void showServerSelector(Player player);
	
	/**
	 * Get the plugin running the API
	 * @return
	 */
	public Plugin getGBPlugin();
}
