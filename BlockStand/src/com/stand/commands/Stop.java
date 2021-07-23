
package com.stand.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stand.main.Stand;

public class Stop extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	if(!Stand.getInstance().handler.started) {
        	Stand.getInstance().configManager.messages.send(player, "invalid_stop");
    		return;
    	}
    	
    	Stand.getInstance().configManager.messages.send(player, "game_stopped");
    	Stand.getInstance().handler.stop();
    	
    }

    @Override

    public String name() {
        return "stop";
    }

    @Override
    public String info() {
        return "Stop the stand game.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Stand.getInstance().configManager.permissions.admin;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		
		List<SubCommand> commands = Stand.getInstance().CommandManager.getCommands();
		
		for(SubCommand i : commands) {
			tabCompleter.createEntry(i.name());
		}
		
		return tabCompleter;
	}
	

}