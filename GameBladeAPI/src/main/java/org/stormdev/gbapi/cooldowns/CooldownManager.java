package org.stormdev.gbapi.cooldowns;

import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.stormdev.gbapi.core.APIProvider;
import org.stormdev.gbapi.storm.misc.MetadataValue;

public class CooldownManager {
	public static final String TRADE_META = "trade.cooldown.meta"; //Cooldown in seconds
	public static final String CONVERT_META = "convert.cooldown.meta";
	
	public static class Meta {
		private long startTimeMS;
		private long seconds;
		
		private Meta(long time, TimeUnit unit){
			this.startTimeMS = System.currentTimeMillis();
			this.seconds = TimeUnit.SECONDS.convert(time, unit);
		}
		
		public long getStartTimeMS(){
			return startTimeMS;
		}
		
		public long getCooldownS(){
			return this.seconds;
		}
		
		public long getCooldownMS(){
			return TimeUnit.MILLISECONDS.convert(getCooldownS(), TimeUnit.SECONDS);
		}
		
		public long getElapsedTimeMS(){
			return System.currentTimeMillis() - getStartTimeMS();
		}
		
		public boolean hasExpired(){
			return getElapsedTimeMS() > getCooldownMS();
		}
	}
	
	public static void setCoolDown(String META, Player player, long time, TimeUnit unit){
		removeCoolDown(META, player);
		player.setMetadata(META, new MetadataValue(new Meta(time, unit), APIProvider.getAPI().getGBPlugin()));
	}
	
	public static boolean hasCooldown(String META, Player player){
		return getRemainingCoolDownTimeMS(META, player) > 0;
	}
	
	public static void removeCoolDown(String META, Player player){
		player.removeMetadata(META, APIProvider.getAPI().getGBPlugin());
	}
	
	public static long getRemainingCoolDownTimeMS(String META, Player player){
		Meta m = getCoolDown(META, player);
		if(m == null){
			return 0;
		}
		return m.getCooldownMS()-m.getElapsedTimeMS();
	}
	
	public static long getRemainingCoolDownTimeS(String META, Player player){
		return TimeUnit.SECONDS.convert(getRemainingCoolDownTimeMS(META, player), TimeUnit.MILLISECONDS);
	}
	
	public static long getRemainingCoolDownTimeMins(String META, Player player){
		return (long) (getRemainingCoolDownTimeS(META, player) / 60.0);
	}
	
	public static Meta getCoolDown(String META, Player player){
		if(!player.hasMetadata(META)){
			return null;
		}
		try {
			Meta m = (Meta) player.getMetadata(META).get(0).value();
			if(m.hasExpired()){
				player.removeMetadata(META, APIProvider.getAPI().getGBPlugin());
				return null;
			}
			return m;
		} catch (Exception e) {
			return null;
		}
	}
}
