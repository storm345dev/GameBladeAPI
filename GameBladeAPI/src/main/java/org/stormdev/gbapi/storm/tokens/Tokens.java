package org.stormdev.gbapi.storm.tokens;

import org.bukkit.entity.Player;

/**
 * API to manipulate player's tokens
 *
 */
public interface Tokens {
	public static class TokenServiceUnavailableException extends Exception {
		private static final long serialVersionUID = 1L;
		
	}
	
	/** 
	 * Get the token balance for a player. Use this ASYNCHRONOUSLY (Not in main thread) as it's a long call to servermanager.
	 * 
	 * @param player The player to get the tokens for
	 * @return Their token balance
	 * @throws TokenServiceUnavailableException Thrown if unable to find the token service
	 */
	public int getTokens(Player player) throws TokenServiceUnavailableException;
	
	/**
	 * Give a player some tokens, use async if possible
	 * 
	 * @param playerName The name of the player
	 * @param tokens The amount of tokens to give
	 */
	public void awardTokens(final String playerName, final int tokens);
	
	/**
	 * Give a player some tokens, use async if possible
	 * 
	 * @param player The player to give tokens to
	 * @param tokens The amount of tokens to award
	 */
	public void awardTokens(final Player player, final int tokens);
	
	/**
	 * Take away tokens from a player, use async if possible
	 * 
	 * @param playerName The name of the player
	 * @param tokens The amount of tokens to take
	 */
	public void takeTokens(final String playerName, final int tokens);
	
	/**
	 * Take away tokens from a player, use async if possible
	 * 
	 * @param player The player to take tokens from
	 * @param tokens The amount of tokens to take
	 */
	public void takeTokens(final Player player, final int tokens);
}


