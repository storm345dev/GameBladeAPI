package org.stormdev.gbapi.stars;

import org.bukkit.entity.Player;

/**
 * Stars are used for COSMETICS only
 *
 */
public interface Stars {
	/**
	 * Get how many stars a player has; use ASYNCHRONOUSLY
	 * @param player The player to get the balance of
	 * @return
	 */
	public int getStars(Player player);
	
	/**
	 * Give a player some stars
	 * @param player The player to give stars to
	 * @param stars The amount to give
	 */
	public void awardStars(Player player, int stars);
	
	/**
	 * Takes away some stars from players
	 * @param player The player to take stars from
	 * @param stars The number to take
	 */
	public void takeStars(Player player, int stars);
	
	/**
	 * Set a player's star balance
	 * @param player The player to set the balance of
	 * @param stars The amount
	 */
	public void setStars(Player player, int stars);
	
	/**
	 * Get how many stars a player has; use ASYNCHRONOUSLY
	 * @param uuid The uuid of player to get the balance of
	 * @return Their balance
	 */
	public int getStars(String uuid);
	
	/**
	 * Give a player some stars
	 * @param uuid The uuid of the player to give stars to
	 * @param stars The amount to give
	 */
	public void awardStars(String uuid, int stars);
	
	/**
	 * Takes away some stars from players
	 * @param uuid The uuid of player to take stars from
	 * @param stars The number to take
	 */
	public void takeStars(String uuid, int stars);
	
	/**
	 * Set a player's star balance
	 * @param uuid The uuid of player to set the balance of
	 * @param stars The amount
	 */
	public void setStars(String uuid, int stars);
}
