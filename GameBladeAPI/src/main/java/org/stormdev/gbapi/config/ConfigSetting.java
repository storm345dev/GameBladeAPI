package org.stormdev.gbapi.config;

public class ConfigSetting<T> implements ConfigValue<T> {
	private String key;
	private T def;
	private T val;
	
	public ConfigSetting(String configKey, T defaultValue){
		this.key = configKey;
		this.def = defaultValue;
		this.val = def;
	}
	
	@Override
	public T getDefaultValue() {
		return def;
	}

	@Override
	public T getValue() {
		return val;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) {
		this.val = (T)value;
	}

	@Override
	public String getConfigKey() {
		return key;
	}

}
