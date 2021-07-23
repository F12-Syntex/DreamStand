package com.stand.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.stand.handlers.PlayerData;
import com.stand.handlers.StandHandler;
import com.stand.main.Stand;

public class MovementHandler extends SubEvent{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "move handler";
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "handles the movement events.";
	}
	
	@EventHandler
	public void onChat(PlayerMoveEvent e) {
		
		StandHandler handler = Stand.getInstance().handler;
		
		if(!handler.started) {
			return;
		}
		
		final Location goal = e.getPlayer().getLocation();
		
		PlayerData data = Stand.getInstance().handler.playerData.getUser(e.getPlayer());
		
		if(goal.getBlock().getType() == data.getBlock() ||
		   goal.add(0, -1, 0).getBlock().getType() == data.getBlock() ||
		   goal.add(0, 1, 0).getBlock().getType() == data.getBlock()) {
		
			if(!data.isFoundBlock()) {
				handler.blockFound(e.getPlayer());
			}
		}
		
	}

}
