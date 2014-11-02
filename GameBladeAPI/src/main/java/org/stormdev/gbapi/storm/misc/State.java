package org.stormdev.gbapi.storm.misc;

public enum State {
	UNKNOWN(false, true), TRUE(true, false), FALSE(false, false);
	
	private boolean value;
	private boolean unknown;
	
	private State(boolean value, boolean unknown){
		this.value = value;
		this.unknown = unknown;
	}
	
	public boolean isTrue(){
		return value && !unknown;
	}
	
	public boolean isFalse(){
		return !value && !unknown;
	}
	
	public boolean isUnknown(){
		return unknown;
	}
}
