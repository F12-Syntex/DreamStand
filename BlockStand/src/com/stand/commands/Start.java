
package com.stand.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stand.handlers.GameType;
import com.stand.main.Stand;
import com.stand.utils.MessageUtils;
import com.stand.utils.Numbers;

public class Start extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	if(args.length != 3) {
    		MessageUtils.sendMessage(player, "&c/" + Stand.getInstance().CommandManager.main + " " + this.name() + " {bestof | firstto} {number}");
    		return;
    	}
    	
    	
    	if(Stand.getInstance().handler.started) {
        	Stand.getInstance().configManager.messages.send(player, "invalid_start");
    		return;
    	}
    	
    	String gameType = args[1];
    	String value = args[2];
    	
    	System.out.println(gameType);
    	System.out.println(value);
    	
    	if(gameType.equalsIgnoreCase("firstto")) {
    		if(Numbers.isNumber(value)) {
    			Stand.getInstance().configManager.messages.send(player, "game_started");
    	    	Stand.getInstance().handler.start(Integer.parseInt(value), GameType.FIRSTTO);
    	    }else {
        		MessageUtils.sendMessage(player, "&c/" + Stand.getInstance().CommandManager.main + " " + this.name() + " {bestof | firstto} {number}");
    		}
    	}
    	if(gameType.equalsIgnoreCase("bestof")) {
    		if(Numbers.isNumber(value)) {
    			Stand.getInstance().configManager.messages.send(player, "game_started");
    	    	Stand.getInstance().handler.start(Integer.parseInt(value), GameType.BESTOF);
    	    }else {
        		MessageUtils.sendMessage(player, "&c/" + Stand.getInstance().CommandManager.main + " " + this.name() + " {bestof | firstto} {number}");
    		}
    	}
    	
    }

    @Override

    public String name() {
        return "start";
    }

    @Override
    public String info() {
        return "Starts the stand game.";
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
		
		tabCompleter.createEntry("bestof.3");
		tabCompleter.createEntry("bestof.5");
		tabCompleter.createEntry("bestof.7");
		
		tabCompleter.createEntry("firstto.3");
		tabCompleter.createEntry("firstto.4");
		tabCompleter.createEntry("firstto.5");
		
		return tabCompleter;
	}
	

}