package org.stormdev.gbapi.storm.misc;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.entity.Player;

public class JSONChat {
	public static void send(Player player, String chat){
		try {
			
			/*Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> PacketPlayOutChat = Reflect.getNMSClass("PacketPlayOutChat");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);*/
			
			IChatBaseComponent o = ChatSerializer.a(chat);/*aChatSerializer.invoke(null, chat);*/
			Object packet = new PacketPlayOutChat(o);/*PacketPlayOutChat.getConstructor(IChatBaseComponent).newInstance(IChatBaseComponent.cast(o));*/
			Reflect.sendPacket(player, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		/*
		IChatBaseComponent comp = ChatSerializer.a(chat);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		*/
	}
	
	public static String generateJSONHyperLink(String text, String actionCommand, boolean underline){
		//{text:\"Click.\", underlined:true, clickEvent:{ action:run_command, value:\"/say Clicked.\" }}
		String s = "{text:\""+text+"\", underlined:"+underline+", clickEvent:{ action:run_command, value:\""+actionCommand+"\" }}";
		return s;
	}
}
