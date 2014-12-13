package org.stormdev.gbapi.cosmetics;

import java.util.List;

import org.bukkit.entity.Player;

/**
 * 
 * Manage cosmetics
 *
 */
public interface Cosmetics {
	
	/**
	 * Get the manager for setting and retrieving active cosmetics such as hats and vehicle colour
	 * @return The manager for ..
	 */
	public ActiveCosmeticManager getActiveCosmeticManager();
	
	/**
	 * Check if a player owns a cosmetic
	 * USE ASYNCHRONOUSLY
	 * 
	 * @param player The player to check if they own the cosmetic
	 * @param cosmeticId The ID of the cosmetic
	 * @return True if they own it
	 */
	public boolean ownsCosmetic(Player player, String cosmeticId);
	
	/**
	 * Get all cosmetics of a certain type
	 * USE ASYNCHRONOUSLY
	 * @param type The type of cosmetic
	 * @return All cosmetics of that type
	 */
	public List<Cosmetic> getAllByType(CosmeticType type);
	
	/**
	 * Get all cosmetics a player owns
	 * USE ASYNCHRONOUSLY
	 * @param player The player to get them from
	 * @return A list of owned cosmetics
	 */
	public List<Cosmetic> getOwnedCosmetics(Player player);
	
	/**
	 * Get a list of the ids of all cosmetics a player owns
	 * USE ASYNCHRONOUSLY
	 * @param player The player
	 * @return A list of owned cosmetic's ids
	 */
	public List<String> getOwnedCosmeticIds(Player player);
	
	/**
	 * Clear a players' hat
	 * @param player The player
	 */
	public void clearHat(Player player);
		
	/**
	 * Recalculate a players' hat
	 * @param player the player
	 */
	public void recalculateHat(Player player);
	
	
}
