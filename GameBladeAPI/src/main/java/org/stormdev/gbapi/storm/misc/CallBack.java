package org.stormdev.gbapi.storm.misc;

import org.bukkit.entity.Player;
import org.stormdev.gbapi.core.APIProvider;

public class CallBack {
	public static String META = "callback";
	
	public static interface Handle {
		public void handle(Player player);
	}
	
	private Handle handle;
	
	public CallBack(Player player, Handle handle){
		player.removeMetadata(META, APIProvider.getAPI().getGBPlugin());
		this.handle = handle;
		player.setMetadata(META, new MetadataValue(this, APIProvider.getAPI().getGBPlugin()));
	}
	
	public void callBack(Player player){
		handle.handle(player);
		player.removeMetadata(META, APIProvider.getAPI().getGBPlugin());
	}
	
	public Handle getHandle(){
		return handle;
	}
}
