package org.stormdev.gbapi.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigBase implements Config {
	private YamlConfiguration config = new YamlConfiguration();
	private File file;
	
	public ConfigBase(File file){
		this.file = file;
		
		file.getParentFile().mkdirs();
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				//Oops
				e.printStackTrace();
			}
		}
		
		try {
			config.load(file);
		} catch (Exception e) {
			//Say
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		for(ConfigValue<?> cv:getSettings()){
			if(!config.contains(cv.getConfigKey())){
				config.set(cv.getConfigKey(), cv.getDefaultValue());
			}
			
			cv.setValue(config.get(cv.getConfigKey()));
		}
		save();
	}

	@Override
	public void reload() {
		load();
	}
	
	private ConfigValue<?>[] getSettings(){
		Field[] fs = this.getClass().getDeclaredFields();
		List<ConfigValue<?>> vals = new ArrayList<ConfigValue<?>>();
		
		for(Field f:fs){
			try {
				f.setAccessible(true);
				Object v = f.get(null);
				if(v instanceof ConfigValue){
					vals.add((ConfigValue<?>) v);
				}
			} catch (Exception e) {
				continue;
			}
		}
		
		return vals.toArray(new ConfigValue<?>[]{});
	}

	@Override
	public YamlConfiguration getConfig() {
		return config;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			// Say
			e.printStackTrace();
		}
	}

	@Override
	public void writeDefaults() {
		for(ConfigValue<?> cv:getSettings()){
			config.set(cv.getConfigKey(), cv.getDefaultValue());
		}
		save();
	}

}
