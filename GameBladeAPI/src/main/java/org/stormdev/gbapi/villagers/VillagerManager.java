package org.stormdev.gbapi.villagers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Manages villagers used for menus, etc
 *
 */
public interface VillagerManager {
	/**
	 * Listener for when villagers are right clicked on
	 *
	 */
	public static interface VillagerListener {
		/**
		 * Called when a player right clicks on the villager
		 * @param player The player who right clicked
		 */
		public void onInteract(Player player);
	}
	
	/**
	 * 
	  * Register a listener for when villagers of the given name are right clicked.
	 * 
	 * @param villagerName The name of the villager to listen to
	 * @param listener The listener for when they are right clicked
	 * @throws VillagerAlreadyRegisteredException Thrown when you try to register a villager who is already registered
	 */
	public void registerVillagerListener(String villagerName, VillagerListener listener) throws VillagerAlreadyRegisteredException;
	
	/**
	 * Check if a villager of the given name is already registered
	 * @param name The name of the villager
	 * @return True if already registered
	 */
	public boolean isVillagerRegistered(String name);
	
	/**
	 * Spawns an interactable villager at the given location
	 * @param villagerName The name of the villager
	 * @param location The location of the villager
	 * @return The spawned villager
	 */
	public Villager spawnVillager(String villagerName, Location location);
	
	public static class VillagerAlreadyRegisteredException extends Exception {
		private static final long serialVersionUID = 7993433635153385561L;
		public VillagerAlreadyRegisteredException(){
			
		}
	}
}
