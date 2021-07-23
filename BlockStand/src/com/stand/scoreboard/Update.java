package com.stand.scoreboard;

import org.bukkit.entity.Player;

public interface Update {
	public ScoreBoardText get(Player player, org.bukkit.scoreboard.Scoreboard scoreboard);
	public boolean run();
}
