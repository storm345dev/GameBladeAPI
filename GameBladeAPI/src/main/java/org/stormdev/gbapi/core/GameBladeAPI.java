package org.stormdev.gbapi.core;

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
}
