package org.stormdev.gbapi.config;

import java.io.File;

public class DemoConfig  extends ConfigBase {
	public static ConfigSetting<Boolean> someSetting = new ConfigSetting<Boolean>("general.mysettings.someSetting", true);
	
	public DemoConfig(File file) {
		super(file);
		super.load();
		System.out.println("someSetting value is "+someSetting.getValue());
	}

}
