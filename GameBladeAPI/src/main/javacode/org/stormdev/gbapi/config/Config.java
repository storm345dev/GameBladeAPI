package org.stormdev.gbapi.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public interface Config {
	
	/**
	 * Loads the config and writes the default values if they aren't present
	 */
	public void load();
	
	/**
	 * Releads the config
	 */
	public void reload();
	
	/**
	 * Saves the config
	 */
	public void save();
	
	/**
	 * Get the Bukkit config object
	 * @return The config object
	 */
	public YamlConfiguration getConfig();
	
	/**
	 * The file used for the config
	 * @return The file
	 */
	public File getFile();
	
	/**
	 * Resets the config to the default values
	 */
	public void writeDefaults();
}
