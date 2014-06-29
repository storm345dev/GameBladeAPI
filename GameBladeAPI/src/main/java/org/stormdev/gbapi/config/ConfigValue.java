package org.stormdev.gbapi.config;

/**
 * Represents a config value which you want to get the value of
 *
 * @param <T> The type of variable, normally a string
 */
public interface ConfigValue<T> {
	
	/**
	 * Get the default config value for a clean config generation
	 * @return The default val
	 */
	public T getDefaultValue();
	
	/**
	 * Get the current value, when initialised SHOULD be null, until setValue is called
	 * @return The value
	 */
	
	public T getValue();
	
	/**
	 * Set the value, normally reserved for whatever is handling the config loading
	 * @param value
	 */
	public void setValue(Object value);
	
	/**
	 * The string for the config key. <br>
	 * eg. For "general:setting:" <br>
	 * it would be: "general.setting"
	 * @return The config key as a string
	 */
	public String getConfigKey();
}
