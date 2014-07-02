package org.stormdev.gbapi.bans;

import java.util.regex.Pattern;

import org.bukkit.entity.Player;

/**
 * Handling of bans
 *
 */
public interface BanHandler {
	/**
	 * Check if a player is currently banned
	 * @param player The player to check
	 * @return True if banned
	 */
	public boolean isBanned(Player player);
	
	/**
	 * Get the ban reason as a string
	 * @param player The player to get the reason for
	 * @return The reason
	 */
	public String getBanReason(Player player);
	
	/**
	 * Get the time of the ban
	 * @param player The player to get it for
	 * @return The time
	 */
	public Time getBanDuration(Player player);
	
	/**
	 * Ban a player
	 * @param player The player to ban
	 * @param reason The reason to ban them
	 * @param time The time to ban them for
	 */
	public void ban(Player player, String reason, Time time);
	
	/**
	 * The time of a ban
	 *
	 */
	public static class Time {
		private long startTime;
		private long duration;
		private boolean forever = false;
		
		public Time(long durationMS){
			this.duration = durationMS;
			this.startTime = System.currentTimeMillis();
		}
		
		public Time(){
			this.startTime = System.currentTimeMillis();
			this.forever = true;
		}
		
		public boolean hasElapsed(){
			if(forever){
				return false;
			}
			
			long diff = System.currentTimeMillis() - startTime;
			if(diff > duration){
				return true;
			}
			return false;
		}
		
		public String getRemainingTime(){
			if(forever){
				return "Forever";
			}
			
			long diff = System.currentTimeMillis() - startTime;
			diff = Math.round((diff/1000/60));
			
			return diff+" minutes";
		}
		
		@Override
		public String toString(){
			return startTime+"|"+forever+"|"+duration;
		}
		
		public static Time fromString(String in){
			try {
				String[] parts = in.split(Pattern.quote("|"));
				String startTimeRaw = parts[0];
				String foreverRaw = parts[1];
				String durationRaw = parts[2];
				
				Time time = new Time();
				time.duration = Long.parseLong(durationRaw);
				time.forever = Boolean.parseBoolean(foreverRaw);
				time.startTime = Long.parseLong(startTimeRaw);
				return time;
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
}
