package com.stand.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

public class Settings extends Config{

	public int duration = 20;
	public List<String> blocks = new ArrayList<String>();
	
	public Settings(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Settings.Duration", duration));
		
		
		List<String> material = Arrays.asList(Material.values()).stream().filter(i -> i.isSolid() &&
				 !i.name().toLowerCase().contains("purpur") &&
				 !i.name().toLowerCase().contains("end")).map(i -> i.name()).collect(Collectors.toList());
		
		this.items.add(new ConfigItem("Settings.Blocks", material));
		
		
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {
		this.duration = this.getConfiguration().getInt("Settings.Duration");
		this.blocks = this.getConfiguration().getStringList("Settings.Blocks");
	}


	
}
