package net.stormdev.misc;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ObjectWrapper<T> {
	private volatile T value = null;
	
	public ObjectWrapper(){}
	
	public ObjectWrapper(T value){
		this.value = value;
	}
	
	public T getValue(){
		return value;
	}
	
	public void setValue(T val){
		this.value = val;
	}
}

class ExampleUse { //A simple example of how to use it
	{
		final ObjectWrapper<BukkitTask> wrapper = new ObjectWrapper<BukkitTask>();
		wrapper.setValue(Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("pluginName"), new Runnable(){

			public void run() {
				//Do some stuff
				
				//We want to cancel the task?
				wrapper.getValue().cancel(); // :)
			}} , 3l, 3l));
	}
}
