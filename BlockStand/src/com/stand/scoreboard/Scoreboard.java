package com.stand.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import com.stand.main.Stand;
import com.stand.utils.MessageUtils;

public class Scoreboard {

	public List<UUID> players;
	public List<UUID> playersOut;
	private Update update;
	private int ID = 0;
	
	public Scoreboard(Update update) {
		this.players = new ArrayList<UUID>();
		this.playersOut = new ArrayList<UUID>();
		this.update = update;
		
		this.ID = Stand.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Stand.getInstance(), () -> {
			this.timer();
		}, 0L, 20L);
		
	}
	
	public List<Player> getPlayers(){
		
		List<Player> players = new ArrayList<Player>();
		
		for(UUID id : this.players) {
			players.add(Bukkit.getPlayer(id));
		}
		return players;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player.getUniqueId());
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player.getUniqueId());
	}
	
	public void addOutPlayer(Player player) {
		this.players.add(player.getUniqueId());
	}
	
	public void removeOutPlayer(Player player) {
		this.players.remove(player.getUniqueId());
	}
	
	public void timer() {
		for(UUID i : this.players) {
			if(!Bukkit.getOfflinePlayer(i).isOnline()) return;
			Player player = Bukkit.getPlayer(i);
			if(player.isOnline()) {
				this.show(player);
			}
		}
	}
	
	public void close() {
	
		ScoreboardManager manager = Bukkit.getScoreboardManager();
        
		for(UUID i : this.players) {
			Player player = Bukkit.getPlayer(i);
			player.setScoreboard(manager.getMainScoreboard());
			Stand.getInstance().configManager.messages.send(player, "block_ended_message");
			player.setGameMode(GameMode.SURVIVAL);
		}
		
		players.clear();
		Bukkit.getScheduler().cancelTask(ID);
	}
	
	@SuppressWarnings("deprecation")
	public void show(Player player) {
	
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        final org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
        final Objective objective = board.registerNewObjective("test", "dummy");   
    
		ScoreBoardText data = this.update.get(player, board);
        
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(MessageUtils.translateAlternateColorCodes(data.getTitle()));
        
        
        List<Score> scores = data.getBody();
        //Collections.reverse(scores);
        
        int count = scores.size()-1;
        
        
        for(Score i : scores) {
        	objective.getScore(MessageUtils.translateAlternateColorCodes(i.getEntry())).setScore(count);
        	count--;
        }
        
        player.setScoreboard(board);
	
		}
	   
   }

		

