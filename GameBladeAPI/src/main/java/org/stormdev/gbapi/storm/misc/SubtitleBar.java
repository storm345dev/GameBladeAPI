package org.stormdev.gbapi.storm.misc;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;


public class SubtitleBar {
	public static void sendSubtitle(Player player, String message){ //Sends to their chat if 1.7, not 1.8
		try {
			Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> PacketPlayOutChat = Reflect.getNMSClass("PacketPlayOutChat");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);
			
			/*Class<?> c = PacketPlayOutChat.class;
			for(Constructor<?> con:c.getDeclaredConstructors()){
				StringBuilder sb = new StringBuilder();
				for(Class<?> param:con.getParameterTypes()){
					sb.append("[").append(param.getName()).append("]");
				}
				System.out.println(sb.toString());
			}*/
			
			Object o = aChatSerializer.invoke(null, message);
			Object packet = PacketPlayOutChat.getConstructor(IChatBaseComponent, byte.class).newInstance(IChatBaseComponent.cast(o), (byte)2);
			Reflect.sendPacket(player, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
