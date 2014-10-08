package org.stormdev.gbapi.storm.misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;
import org.spigotmc.ProtocolInjector.PacketTitle;
import org.stormdev.gbapi.core.APIProvider;

public class Popups {
	public static void setTabHeader(Player player, String header, String footer){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);
			
			Object head = aChatSerializer.invoke(null, new Gson().toJson(header));
			Object foot = aChatSerializer.invoke(null, new Gson().toJson(footer));
			
			Constructor<?> con = ProtocolInjector.PacketTabHeader.class.getConstructor(IChatBaseComponent, IChatBaseComponent);
			
			Reflect.sendPacket(player, con.newInstance(IChatBaseComponent.cast(head), IChatBaseComponent.cast(foot)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showTitle(Player player, String title, int fadeIn, int stay, int fadeOut, ProtocolInjector.PacketTitle.Action action){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);
			
			Object o = aChatSerializer.invoke(null, new Gson().toJson(title));
			
			Constructor<?> con = ProtocolInjector.PacketTitle.class.getConstructor(action.getClass(), IChatBaseComponent);
			
			Object titlePacket = con.newInstance(action, IChatBaseComponent.cast(o));
			Object showPacket = new ProtocolInjector.PacketTitle(PacketTitle.Action.TIMES, fadeIn, stay, fadeOut);
			
			Reflect.sendPacket(player, showPacket);
			Reflect.sendPacket(player, titlePacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendTitleActionOnlyPacket(Player player, ProtocolInjector.PacketTitle.Action action){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			Object showPacket = new ProtocolInjector.PacketTitle(action);
			
			Reflect.sendPacket(player, showPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendTitleTimesPacket(Player player, int fadeIn, int stay, int fadeOut, ProtocolInjector.PacketTitle.Action action){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			Object showPacket = new ProtocolInjector.PacketTitle(PacketTitle.Action.TIMES, fadeIn, stay, fadeOut);
			
			Reflect.sendPacket(player, showPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendTitlePacket(Player player, String title, ProtocolInjector.PacketTitle.Action action){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> ChatSerializer = Reflect.getNMSClass("ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);
			
			Object o = aChatSerializer.invoke(null, new Gson().toJson(title));
			
			Constructor<?> con = ProtocolInjector.PacketTitle.class.getConstructor(action.getClass(), IChatBaseComponent);
			
			Object titlePacket = con.newInstance(action, IChatBaseComponent.cast(o));
			
			Reflect.sendPacket(player, titlePacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
