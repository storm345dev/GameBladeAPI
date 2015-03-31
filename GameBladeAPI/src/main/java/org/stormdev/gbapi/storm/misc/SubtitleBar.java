package org.stormdev.gbapi.storm.misc;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.entity.Player;

import com.google.gson.Gson;


public class SubtitleBar {
	public static void sendSubtitle(Player player, String message){ //Sends to their chat if 1.7, not 1.8
		try {
		/*	Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> PacketPlayOutChat = Reflect.getNMSClass("PacketPlayOutChat");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);*/
			
			/*Class<?> c = PacketPlayOutChat.class;
			for(Constructor<?> con:c.getDeclaredConstructors()){
				StringBuilder sb = new StringBuilder();
				for(Class<?> param:con.getParameterTypes()){
					sb.append("[").append(param.getName()).append("]");
				}
				System.out.println(sb.toString());
			}*/
			
			/*JsonParser jp = new JsonParser();
			JsonReader jr = new JsonReader(new StringReader(message));
			jr.setLenient(true);
			message = jp.parse(jr).getAsString();*/
			
			message = new Gson().toJson(message);
			
			IChatBaseComponent o = ChatSerializer.a(message);
			Object packet = new PacketPlayOutChat(o, (byte) 2);
			/*Object packet = PacketPlayOutChat.getConstructor(IChatBaseComponent, byte.class).newInstance(IChatBaseComponent.cast(o), (byte)2);*/
			Reflect.sendPacket(player, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendJSONSubtitle(Player player, String message){ //Sends to their chat if 1.7, not 1.8
		try {
			/*Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> PacketPlayOutChat = Reflect.getNMSClass("PacketPlayOutChat");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);*/
			
			/*Class<?> c = PacketPlayOutChat.class;
			for(Constructor<?> con:c.getDeclaredConstructors()){
				StringBuilder sb = new StringBuilder();
				for(Class<?> param:con.getParameterTypes()){
					sb.append("[").append(param.getName()).append("]");
				}
				System.out.println(sb.toString());
			}*/
			
			IChatBaseComponent o = ChatSerializer.a(message);
			Object packet = new PacketPlayOutChat(o, (byte) 2);
			Reflect.sendPacket(player, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
