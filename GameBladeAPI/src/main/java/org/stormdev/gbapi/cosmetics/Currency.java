package org.stormdev.gbapi.cosmetics;

public enum Currency {
TOKENS("Tokens"), STARS("Stars");

	private String name;
	private Currency(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}
