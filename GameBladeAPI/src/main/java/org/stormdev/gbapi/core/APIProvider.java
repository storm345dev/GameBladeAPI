package org.stormdev.gbapi.core;


public class APIProvider {
	private static volatile GameBladeAPI api = null;
	
	@SuppressWarnings("unused")
	private static void setAPI(GameBladeAPI ap){
		if(api != null){
			return;
		}
		api = ap;
	}
	
	/** Returns the API for you to use
	 * 
	 */
	public static GameBladeAPI getAPI(){
		return api;
	}
}
