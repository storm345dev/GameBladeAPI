package org.stormdev.gbapi.cosmetics;

import org.bukkit.entity.Player;

public interface Cosmetic {
	public String toString();
	public String getID();
	public CosmeticType getType();
	public void apply(Player player);
	public void remove(Player player);
	public void justBought(Player player);
	public double getPrice();
	public String getUserFriendlyName();
	public Currency getCurrencyUsed();
}
