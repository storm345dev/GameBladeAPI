package org.stormdev.gbapi.notifications;

import java.util.List;

import org.bukkit.entity.Player;

public interface Notifications {
	/**
	 * Get a player's notifications, run this asynchronously
	 * 
	 * @param uuid The player's UUID
	 * @return A list of their outstanding notifications
	 */
	public List<String> getNotifications(String uuid);
	
	/**
	 * Set a player's notifications
	 * 
	 * @param uuid The player's UUID
	 * @param notifications Their notifications
	 */
	public void setNotifications(String uuid, List<String> notifications);
	
	/**
	 * Clear a player's notifications
	 * 
	 * @param uuid The UUID of the player
	 */
	public void clearNotifications(String uuid);
	
	/**
	 * Add a player's notification
	 * 
	 * @param uuid The UUID of the player
	 * @param notification The notification to send them
	 */
	public void addNotification(String uuid, String notification);
	
	/**
	 * Show a player all their pending notifications
	 * 
	 * @param player The player to notify
	 */
	public void showNotifications(Player player);
}
