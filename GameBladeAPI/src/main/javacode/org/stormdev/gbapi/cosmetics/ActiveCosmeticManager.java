package org.stormdev.gbapi.cosmetics;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

public interface ActiveCosmeticManager {
	/**
	 * Get the active cosmetic ID for a player for a certain type of cosmetic; eg. hat
	 * 
	 * @param type The type of cosmetic
	 * @param player The player to look it up for
	 * @return Null if none; or id
	 */
	public String getActiveCosmeticIDForType(CosmeticType type, Player player);
	
	/**
	 * Get the active cosmetic ID for a player for a certain type of cosmetic; eg. hat
	 * 
	 * @param type The type of cosmetic
	 * @param uuid The uuid of the player to look it up for
	 * @return Null if none; or id
	 */
	public String getActiveCosmeticIDForType(CosmeticType type, String uuid);
	
	/**
	 * Sets a player's active cosmetic for a type, eg. hat
	 * 
	 * @param player The player to set it for
	 * @param type The type of cosmetic
	 * @param id The id of the active cosmetic or null to set to clear
	 */
	public void setActiveCosmeticIDForType(Player player, CosmeticType type, String id);
	
	/**
	 * Sets a player's active cosmetic for a type, eg. hat
	 * 
	 * @param uuid The uuid of the player to set it for
	 * @param type The type of cosmetic
	 * @param id The id of the active cosmetic or null to set to clear
	 */
	public void setActiveCosmeticIDForType(String uuid, CosmeticType type, String id);
	
	/**
	 * Fills a minecart vehicle with the players cosmetics if they have them
	 * 
	 * @param cart The minecart to fill
	 * @param player The player to fill the vehicle of
	 */
	public void fillMinecartVehicle(Minecart cart, Player player);
	
	/**
	 * Clears a minecart of it's cosmetics
	 * @param cart The cart to clear
	 */
	public void clearMinecartOfCosmetics(Minecart cart);
}
