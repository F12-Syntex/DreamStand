package com.stand.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.stand.main.Stand;
import com.stand.placeholder.time.TimeFormater;
import com.stand.scoreboard.ScoreBoardText;
import com.stand.scoreboard.ScoreMaker;
import com.stand.scoreboard.Update;
import com.stand.tags.TagFactory;
import com.stand.utils.Blocks;
import com.stand.utils.MessageUtils;
import com.stand.utils.Time;

public class StandHandler {
	
	public boolean started = false;
	
	public Long startedTime = 0L;
	
	private com.stand.scoreboard.Scoreboard scoreboard;
	
	private int ID = 0;
	private int round = 1;
	private int rounds = 3;
	
	private GameType type;
	
	//private Map<UUID, Integer[]> userScores = new HashMap<UUID, Integer[]>();
	
	public PlayerDataManager playerData;
	
	//private Material block = Material.ACACIA_STAIRS;
	
	public void start(int rounds, GameType type) {
		
		this.rounds = rounds;
		this.setType(type);
		
		this.playerData = new PlayerDataManager();
		
		this.started = true;
		this.startedTime = System.currentTimeMillis();
		
		this.scoreboard = new com.stand.scoreboard.Scoreboard(new Update() {
			@Override
			public ScoreBoardText get(Player player, Scoreboard scoreboard) {
				// TODO Auto-generated method stub
				
				PlayerData userData = playerData.getUser(player);
				
				ScoreMaker maker = new ScoreMaker(scoreboard);
				
				TimeFormater time = new TimeFormater();
				
				int timePassed = (int)Time.difference(Time.getCurrentTimeInDate(), new Date(userData.getTime()));
				int maxTime = Stand.getInstance().configManager.settings.duration;
				int timeLeft = maxTime - timePassed;
				
				if(userData.isFoundBlock()) {
					maker.build("&b&lTime Remaining: &aWaiting");
				}else {
					if(timeLeft <= 10) {
						maker.build("&b&lTime Remaining: &c&l" + timeLeft + "s");
					}else {
						maker.build("&b&lTime Remaining: &7" + time.parse(timeLeft));
					}
				}
				
				
				maker.build("&a&lBlock: &6" + Blocks.safeBlockNames(userData.getBlock()));
				
				maker.build("");
				
				//char code = 9679;
				//char code2 = 9711;
				
				char code = 9898;
				char code2 = 9899;
				
				for(UUID x : StandHandler.this.scoreboard.players) {
					
					OfflinePlayer i = Bukkit.getOfflinePlayer(x);
					String score = "";
					
					PlayerData dat = playerData.getUser(i.getUniqueId());
					
					for(Integer o : dat.getScores()) {
						
						if(o == 1) {
							score += " " + code2;
						}else {
							score += " " + code;
						}
				
					}
					
					
					if(i.isOnline()) {
						if(StandHandler.this.scoreboard.playersOut.contains(i.getUniqueId())) {
							maker.build("• &c" + ChatColor.stripColor(i.getPlayer().getDisplayName()) + " " + score);
						}else{
							
							if(!i.isOnline()) {
								maker.build("• &7" + ChatColor.stripColor(i.getPlayer().getDisplayName()) + " " + score);
							}else {
								maker.build("• &6" + ChatColor.stripColor(i.getPlayer().getDisplayName()) + " " + score);	
							}
							
						}
					}else {
						if(StandHandler.this.scoreboard.playersOut.contains(i.getUniqueId())) {
							maker.build("• &c" + ChatColor.stripColor(i.getName()) + " " + score);
						}else{
							
							if(!i.isOnline()) {
								maker.build("• &7" + ChatColor.stripColor(i.getName()) + " " + score);
							}else {
								maker.build("• &6" + ChatColor.stripColor(i.getName()) + " " + score);	
							}
							
						}
					}
					
					
					
					
					
				}
				
	
				
				return new ScoreBoardText("&6&lStand", maker.getCurrentScore());
			}

			@Override
			public boolean run() {
				return started;
			}
		});
		
		
		
		
		for(Player player : Bukkit.getOnlinePlayers()) {
        	this.playerData.addUser(player.getUniqueId());
			PlayerData userData = playerData.getUser(player);
			userData.setBlock(this.randomBlock());
			Stand.getInstance().configManager.messages.send(player, "block_find_message", "%block%", Blocks.safeBlockNames(userData.getBlock()));
        	this.scoreboard.addPlayer(player);
        	//this.userScores.put(player.getUniqueId(), new Integer[] {0,0,0});
		}
	
		
		
		this.ID = Stand.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Stand.getInstance(), () -> {
		
			List<Player> alive = new ArrayList<Player>();
			
			for(UUID x : StandHandler.this.scoreboard.players) {
				
				if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
				
				Player user = Bukkit.getPlayer(x);
				
				PlayerData userData = playerData.getUser(user);
			
				if(userData.isPlayerOut()) continue;
				
				int timePassed = (int)Time.difference(Time.getCurrentTimeInDate(), new Date(userData.getTime()));
				int maxTime = Stand.getInstance().configManager.settings.duration;
				int timeLeft = maxTime - timePassed;
				
				if(timeLeft <= 0 && !userData.isFoundBlock()) {
					userData.setPlayerOut(true);
					List<Integer> scores = userData.getScores();
					scores.add(0);
					Stand.getInstance().configManager.messages.send(user, "player_lost");
					user.setGameMode(GameMode.SPECTATOR);
				}
		
				if(!userData.isPlayerOut()) {
					alive.add(user);
				}
				
			}
			
			System.out.println(alive.size());
			
			if(alive.size() == 0) {
				for(UUID x : StandHandler.this.scoreboard.players) {
					
					if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
					
					Player player = Bukkit.getPlayer(x);
					
					if(!player.isOnline()) continue;
					
					
					TagFactory factory = TagFactory.instance("");
					
					factory.addMapping("%winner%", "&c&lNo one");
					
					MessageUtils.sendRawMessage(player, factory.playerParse(Stand.getInstance().configManager.messages.getMessage("next_round")));
				}
				
				this.nextRound();
				return;
			}
			
			if(alive.size() == 1) {
				Player user = alive.get(0);
				
				PlayerData lastPlayer = playerData.getUser(user);
				List<Integer> scores = lastPlayer.getScores();
				
				
				scores.add(1);
				lastPlayer.resetTime();
				lastPlayer.setBlock(this.randomBlock());
				
				
				if(this.type == GameType.FIRSTTO) {
					
					for(UUID x : StandHandler.this.scoreboard.players) {
						
						if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
						
						Player player = Bukkit.getPlayer(x);

						PlayerData info = playerData.getUser(player);
						
						if(info.wins() >= this.rounds) {
							for(Player o : this.scoreboard.getPlayers()) {
								
								if(!o.isOnline()) continue;
								
								TagFactory factory = TagFactory.instance("");
								factory.addMapping("%winner%", ChatColor.stripColor(player.getDisplayName())+"");
								MessageUtils.sendRawMessage(o, factory.playerParse(Stand.getInstance().configManager.messages.getMessage("game_end")));
							}
							this.stop();
							return;
						}
					
					}
					
				}
				
				if(this.type == GameType.BESTOF) {
					
					if(this.round >= this.rounds) {

						Player mostPoints = this.scoreboard.getPlayers().get(0);
						
						for(Player player : this.scoreboard.getPlayers()) {

							PlayerData most = playerData.getUser(mostPoints);
							PlayerData info = playerData.getUser(player);
							
							if(info.wins() > most.wins()) {
								mostPoints = player;
							}
						
						}
						
						for(UUID x : StandHandler.this.scoreboard.players) {
							
							if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
							
							Player o = Bukkit.getPlayer(x);
							TagFactory factory = TagFactory.instance("");
							factory.addMapping("%winner%", ChatColor.stripColor(mostPoints.getDisplayName())+"");
							MessageUtils.sendRawMessage(o, factory.playerParse(Stand.getInstance().configManager.messages.getMessage("game_end")));
						}
						this.stop();
						return;
						
						
					}
					
				}
				
				
				for(UUID x : StandHandler.this.scoreboard.players) {
					
					if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
					
					Player player = Bukkit.getPlayer(x);
				
					TagFactory factory = TagFactory.instance("");
					
					factory.addMapping("%winner%", ChatColor.stripColor(user.getDisplayName())+"");
					
					MessageUtils.sendRawMessage(player, factory.playerParse(Stand.getInstance().configManager.messages.getMessage("next_round")));
				}
				
				this.nextRound();
				return;
			}
		
		}, 0L, 20L);
		
	}
	
	public void nextRound() {
		
		Location location = null;
		
		for(UUID x : StandHandler.this.scoreboard.players) {
			
			if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
			
			Player i = Bukkit.getPlayer(x);
		
			if(location == null) {
				
				location = i.getLocation().add(ThreadLocalRandom.current().nextInt(100000), 255, ThreadLocalRandom.current().nextInt(100000));
				
				for(int y = 255; y > 0; y--) {
					location.setY(y);
					if(location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.VOID_AIR) {
						break;
					}
				}
			
			}
			
			
			PlayerData user = playerData.getUser(i);
			
			user.resetTime();
			user.setBlock(this.randomBlock());
			user.setPlayerOut(false);
			user.setFoundBlock(false);
			
			i.setGameMode(GameMode.SURVIVAL);
			
			i.teleport(location);
			
			Stand.getInstance().configManager.messages.send(i, "block_find_message", "%block%", Blocks.safeBlockNames(user.getBlock()));
		}
		
		this.round++;
	}
	
	public void stop() {
		this.round = 1;
		this.started = false;
		this.scoreboard.close();
		Stand.getInstance().getServer().getScheduler().cancelTask(this.ID);
	}
	
	public void blockFound(Player player) {
		PlayerData data = playerData.getUser(player);
		if(!data.isPlayerOut()) {
			
			//data.resetTime();
			//data.setBlock(this.randomBlock());
			
			data.setFoundBlock(true);
			
			
			for(UUID x : StandHandler.this.scoreboard.players) {
				
				if(!Bukkit.getOfflinePlayer(x).isOnline()) continue;
				
				Player i = Bukkit.getPlayer(x);
			
				Stand.getInstance().configManager.messages.send(i, "block_found", "%player%", ChatColor.stripColor(player.getDisplayName()));			
				
			}
			

			if(playerData.hasEveryoneFoundTheirBlock()) {
				this.nextRound();
			}
		
		}
	}
	
	public Material randomBlock() {
		
		List<String> blocks = Stand.getInstance().configManager.settings.blocks;
		
		return Material.valueOf(blocks.get(ThreadLocalRandom.current().nextInt(0, blocks.size()-1)));
		
	}
	
	public long getTimePassedInMilliseconds() {
		return System.currentTimeMillis() - this.startedTime;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

}
