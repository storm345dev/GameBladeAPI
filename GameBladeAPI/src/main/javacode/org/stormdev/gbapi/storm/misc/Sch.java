package org.stormdev.gbapi.storm.misc;

import org.bukkit.Bukkit;
import org.stormdev.gbapi.core.APIProvider;

public class Sch {
	public static void runAsync(Runnable run){
		if(!Bukkit.isPrimaryThread()){
			run.run();
			return;
		}
		Bukkit.getScheduler().runTaskAsynchronously(APIProvider.getAPI().getGBPlugin(), run);
	}
	
	public static void notSync(){
		if(Bukkit.isPrimaryThread()){
			throw new RuntimeException("This must not be done in the main thread!");
		}
	}
}
