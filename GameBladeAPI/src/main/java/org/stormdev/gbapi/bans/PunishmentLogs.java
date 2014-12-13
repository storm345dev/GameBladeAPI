package org.stormdev.gbapi.bans;

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

/**
 * View players' previous punishments
 *
 */
public interface PunishmentLogs {
	public static enum PunishmentType {
		WARN("Warn", "Warns", "Warned"), KICK("Kick", "Kicks", "Kicked"), BAN("Ban", "Bans", "Banned");
		
		private String name;
		private String plural;
		private String past;
		private PunishmentType(String name, String plural, String past){
			this.name = name;
			this.plural = plural;
			this.past = past;
		}
		
		public String getUserFriendlyName(){
			return name;
		}
		
		public String getUserFriendlyPlural(){
			return plural;
		}
		
		public String getUserFriendlyPast(){
			return past;
		}
	}
	
	public static class PunishmentLog {
		private PunishmentType type;
		private String reason;
		private String admin;
		private String time;
		
		public PunishmentLog(PunishmentType type,  String reason, String admin, String time){
			this.type = type;
			this.reason = reason;
			this.admin = admin;
			this.time = time;
		}
		
		public String toUserFriendlyString(){
			return time+" "+type.getUserFriendlyPast()+" by "+admin+" for "+reason;
		}
		
		public String toExternalString(){
			return type.name()+"|"+time+"|"+admin+"|"+reason;
		}
		
		public static PunishmentLog fromString(String in){
			try {
				String[] parts = in.split(Pattern.quote("|"));
				String typeRaw = parts[0];
				String time = parts[1];
				String admin = parts[2];
				String reason = parts[3];
				for(int i=4;i<parts.length;i++){
					reason+= " " + parts[i];
				}
				
				PunishmentType type = PunishmentType.valueOf(typeRaw);
				
				return new PunishmentLog(type, reason, admin, time);
			} catch (Exception e) {
				return null;//invalid
			}
		}
	}
	
	/**
	 * Log a players' punishment
	 * @param player The player
	 * @param type The type of punishment to log
	 * @param reason The reason for the punishment
	 * @param admin The name of the admin who punished them
	 */
	public void log(Player player, PunishmentType type, String reason, String admin);
	
	/**
	 * Get all of a players' offenses
	 * @param player The player
	 * @param type The type of offense to get
	 * @return The offenses
	 */
	public List<PunishmentLog> getLogs(Player player, PunishmentType type);
	
	/**
	 * Log a players' punishment
	 * @param uuid The uuid of the player
	 * @param type The type of punishment to log
	 * @param reason The reason for the punishment
	 * @param admin The name of the admin who punished them
	 */
	public void log(String uuid, PunishmentType type, String reason, String admin);
	
	/**
	 * Get all of a players' offenses
	 * @param uuid The uuid of the player
	 * @param type The type of offense to get
	 * @return The offenses
	 */
	public List<PunishmentLog> getLogs(String uuid, PunishmentType type);
	
}
