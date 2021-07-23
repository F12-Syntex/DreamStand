package com.stand.handlers;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerDataManager {
	
	private ArrayList<PlayerData> data;
	
	public PlayerDataManager() {
		this.data = new ArrayList<PlayerData>();
	}

	public void addPlayer(PlayerData data) {
		this.data.add(data);
	}
	
	public void removePlayer(PlayerData data) {
		this.data.remove(data);
	}
	
	public void addUser(UUID uuid) {
		PlayerData data = new PlayerData(uuid, Material.AIR, System.currentTimeMillis(), new ArrayList<Integer>());
		this.data.add(data);
	}
	
	public PlayerData getUser(Player player) {
		return this.getUser(player.getUniqueId());
	}
	
	public boolean hasEveryoneFoundTheirBlock() {
		for(PlayerData i : this.data) {
			if(!i.isFoundBlock()) return false;
		}
		return true;
	}
	
	public PlayerData getUser(UUID uuid) {
		for(PlayerData i : this.data) {
			if(i.getUuid().compareTo(uuid) == 0) return i;
		}
		return null;
	}
	
}
