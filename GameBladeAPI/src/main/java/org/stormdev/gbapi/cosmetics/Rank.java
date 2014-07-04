package org.stormdev.gbapi.cosmetics;

import org.bukkit.entity.Player;

public enum Rank {
DEFAULT("Default", 0, null), 
VIP("Vip", 1, "gameblade.vip"), 
VIP_PLUS("VIP+", 2, "gameblade.vipplus"), 
PREMIUM("Premium", 3, "gameblade.premium"), 
PREMIUM_PLUS("Premium+", 4, "gameblade.premiumplus"),
STAFF("Staff", 5, "gameblade.staff");

	private int pos;
	private String name;
	private String perm;
	private Rank(String name, int pos, String perm){
		this.name = name;
		this.pos = pos;
		this.perm = perm;
	}
	
	public String getPerm(){
		return perm;
	}
	
	public String getName(){
		return name;
	}
	
	private int getPos(){
		return pos;
	}
	
	public boolean canUse(Rank r){
		return r.getPos()<=getPos();
	}
	
	public static Rank getRank(Player player){
		Rank r = Rank.DEFAULT;
		Rank[] defined = Rank.values();
		
		for(Rank rank:defined){
			if(rank.getPerm() != null){
				if(player.hasPermission(rank.getPerm())){
					r = rank;
				}
			}
		}
		
		return r;
	}
}
