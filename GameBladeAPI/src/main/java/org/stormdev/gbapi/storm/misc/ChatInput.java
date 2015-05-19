package org.stormdev.gbapi.storm.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.stormdev.gbapi.core.APIProvider;

public class ChatInput {
	public static final String meta = "gameblade.chatinput";
	private String name;
	private InputValidator iv;
	
	public ChatInput(Player player, InputValidator iv){
		this.iv = iv;
		this.name = player.getName();
		player.setMetadata(meta, new MetadataValue(this, APIProvider.getAPI().getGBPlugin()));
		player.sendMessage(iv.getHelpMessage());
	}
	
	public void onChat(Player player, String in){
		if(!iv.isValid(player, in)){
			player.sendMessage(iv.getInvalidMessage());
			destroy();
			return;
		}
		iv.onValidInput(player, in);
		destroy();
	}
	
	public void destroy(){
		Player player = Bukkit.getPlayer(name);
		if(player != null){
			player.removeMetadata(meta, APIProvider.getAPI().getGBPlugin());
		}
	}
	
	public static interface InputValidator {
		
		public String getHelpMessage();
		public boolean isValid(Player player, String input);
		public void onValidInput(Player player, String input);
		public String getInvalidMessage();
	}
}
