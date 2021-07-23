package com.stand.utils;

import org.bukkit.Material;

public class Blocks {
	
	public static String safeBlockNames(Material material) {
		return StringMinipulation.capitalizeString(material.name().replace("_", " ").toLowerCase());
	}

}
