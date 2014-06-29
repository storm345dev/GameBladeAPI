package org.stormdev.gbapi.servers;

/**
 * Get information about the current server
 *
 */
public interface ServerInfo {
	/**
	 * The ticks-per-second of the server
	 * @return The TPS as a double
	 */
	public double getTPS();
	/**
	 * How many players are online
	 * @return The amount
	 */
	public int getPlayerCount();
	/**
	 * How many players can be online at a time
	 * @return The amount
	 */
	public int getMaxPlayers();
	/**
	 * The resource score % of the server
	 * @return The resource score
	 */
	public int getResourceScore();
	/**
	 * The max RAM the JVM can allocate
	 * @return Max ram
	 */
	public double getMaxRam();
	/**
	 * The currently used amount of ram
	 * @return The current ram
	 */
	public double getUsedRam();
}
