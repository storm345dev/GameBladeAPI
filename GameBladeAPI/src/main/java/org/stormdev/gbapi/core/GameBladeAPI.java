package org.stormdev.gbapi.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.stormdev.gbapi.bans.BanHandler;
import org.stormdev.gbapi.bans.PunishmentLogs;
import org.stormdev.gbapi.cosmetics.Cosmetics;
import org.stormdev.gbapi.servers.ServerInfo;
import org.stormdev.gbapi.stars.Stars;
import org.stormdev.gbapi.storm.tokens.Tokens;
import org.stormdev.gbapi.villagers.VillagerManager;

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
	 * Shows the player the cosmetic panel to edit their cosmetics
	 * @param player The player to show it to
	 */
	public void showCosmeticPanel(Player player);
	
	/**
	 * Get the plugin running the API
	 * @return
	 */
	public Plugin getGBPlugin();
	
	/**
	 * Get the handler for stars (COSMETIC only)
	 * @return The handler for stars
	 */
	public Stars getStarsHandler();
	
	/**
	 * Get the handler for bans
	 * @return The handler for bans
	 */
	public BanHandler getBans();
	
	/**
	 * Get the handler for managing cosmetics
	 * @return The cosmetic handler
	 */
	public Cosmetics getCosmeticsHandler();
	
	/**
	 * Get the handler for logging punishments
	 * @return The handler for logging punishments
	 */
	public PunishmentLogs getPunishmentLogger();
	
	/**
	 * Get the VillagerManager for managing interact-able villagers
	 * @return The VillagerManager
	 */
	public VillagerManager getMenuVillagerManager();
	
	public boolean is1_8(Player player);
}
