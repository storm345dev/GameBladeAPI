package org.stormdev.gbapi.cosmetics;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class VehicleColours {
	public static ItemStack getItemStack(DyeColor color){
		Wool w = new Wool();
		w.setColor(color);
		ItemStack wi = w.toItemStack();
		return new ItemStack(Material.STAINED_CLAY, 1, wi.getDurability());
	}
	
	public static String getCorrectName(String colourName){
		String[] words = colourName.split("_| ");
		StringBuilder out = new StringBuilder();
		for(String word:words){
			word = getCorrectedCaseOfWord(word);
			if(out.length() > 0){
				out.append(" ");
			}
			out.append(word);
		}
		
		return out.toString();
	}
	
	public static String getCorrectedCaseOfWord(String word){
		String lower = word.toLowerCase();
		if(lower.length() < 2){
			return lower;
		}
		String a = lower.substring(0, 1).toUpperCase();
		String b = lower.substring(1);
		return a + b;
	}
}
