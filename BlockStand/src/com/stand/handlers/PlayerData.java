package com.stand.handlers;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;

public class PlayerData {

	private UUID uuid;
	private Material block;
	private long time;
	private List<Integer> scores;
	private boolean playerOut;
	private boolean foundBlock;
	
	public PlayerData(UUID uuid, Material block, long time, List<Integer> scores) {
		this.setUuid(uuid);
		this.block = block;
		this.time = time;
		this.scores = scores;
		
		this.setFoundBlock(false);
		this.setPlayerOut(false);
	}
	
	public Material getBlock() {
		return block;
	}
	public void setBlock(Material block) {
		this.block = block;
	}
	public long getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public void resetTime() {
		this.time = System.currentTimeMillis();
	}
	public List<Integer> getScores() {
		return scores;
	}
	public void setScores(List<Integer> scores) {
		this.scores = scores;
	}
	public int wins() {
		int wins = 0;
		for(int i : this.scores) {
			if(i == 1) wins++;
		}
		return wins;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isPlayerOut() {
		return playerOut;
	}

	public void setPlayerOut(boolean playerOut) {
		this.playerOut = playerOut;
	}

	public boolean isFoundBlock() {
		return foundBlock;
	}

	public void setFoundBlock(boolean foundBlock) {
		this.foundBlock = foundBlock;
	}
	
	
	
}
