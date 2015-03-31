package org.stormdev.gbapi.storm.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle.EnumTitleAction;

import org.bukkit.entity.Player;
import org.stormdev.gbapi.core.APIProvider;

import com.google.gson.Gson;

public class Popups {
	public static enum TitleAction {
		CLEAR(EnumTitleAction.CLEAR),
		RESET(EnumTitleAction.RESET),
		SUBTITLE(EnumTitleAction.SUBTITLE),
		TIMES(EnumTitleAction.TIMES),
		TITLE(EnumTitleAction.TITLE);
		
		private EnumTitleAction val;
		private TitleAction(EnumTitleAction val){
			this.val = val;
		}
		
		public EnumTitleAction getVal(){
			return this.val;
		}
	}
	
	public static void setTabHeader(Player player, String header, String footer){
		
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			/*Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			Class<?> ChatSerializer = Reflect.getNMSClass("IChatBaseComponent.ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);*/
			
			IChatBaseComponent head = ChatSerializer.a(new Gson().toJson(header));/*aChatSerializer.invoke(null, new Gson().toJson(header));*/
			IChatBaseComponent foot = ChatSerializer.a(new Gson().toJson(footer));/*aChatSerializer.invoke(null, new Gson().toJson(footer));*/
			
			/*Class<?> c = PacketPlayOutPlayerListHeaderFooter.class;
			for(Constructor<?> con:c.getDeclaredConstructors()){
				StringBuilder sb = new StringBuilder();
				for(Class<?> param:con.getParameterTypes()){
					sb.append("[").append(param.getName()).append("]");
				}
				System.out.println(sb.toString());
			}*/
			
			try {
				PacketPlayOutPlayerListHeaderFooter pth = new PacketPlayOutPlayerListHeaderFooter();
				
				Field headerField = pth.getClass().getDeclaredField("a");
				headerField.setAccessible(true);
				headerField.set(pth, head);
				
				Field footerField = pth.getClass().getDeclaredField("b");
				footerField.setAccessible(true);
				footerField.set(pth, foot);
				
				Reflect.sendPacket(player, pth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showTitle(Player player, String title, int fadeIn, int stay, int fadeOut, TitleAction action){
		if(!APIProvider.getAPI().is1_8(player)){
			return;
		}
		try {
			//Class<?> IChatBaseComponent = Reflect.getNMSClass("IChatBaseComponent");
			/*Class<?> ChatSerializer = Reflect.getNMSClass("IChatBaseComponent.ChatSerializer");
			Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", String.class);*/
			
			Object o = ChatSerializer.a(new Gson().toJson(title));/*aChatSerializer.invoke(null, new Gson().toJson(title));*/
			
			PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(action.getVal(), (IChatBaseComponent) o, fadeIn, stay, fadeOut);
			PacketPlayOutTitle showPacket = new PacketPlayOutTitle(TitleAction.TIMES.val, (IChatBaseComponent) o, fadeIn, stay, fadeOut);
			
			/*Constructor<?> con = ProtocolInjector.PacketTitle.class.getConstructor(action.getClass(), IChatBaseComponent);
			
			Object titlePacket = con.newInstance(action, IChatBaseComponent.cast(o));
			Object showPacket = new ProtocolInjector.PacketTitle(PacketTitle.Action.TIMES, fadeIn, stay, fadeOut);*/
			
			Reflect.sendPacket(player, showPacket);
			Reflect.sendPacket(player, titlePacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*public static void sendTitleActionOnlyPacket(Player player, ProtocolInjector.PacketTitle.Action action){
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
	}*/
	/*public static void sendTitlePacket(Player player, String title, ProtocolInjector.PacketTitle.Action action){
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
	}*/
	
}
