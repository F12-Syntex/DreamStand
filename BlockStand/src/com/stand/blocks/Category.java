package com.stand.blocks;

import org.bukkit.Material;

public class Category {
	
	private Material material;
	private Tags[] tags;
	
	public Category(Material material, Tags... tags) {}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Tags[] getTags() {
		return tags;
	}

	public void setTags(Tags[] tags) {
		this.tags = tags;
	}

	
}
