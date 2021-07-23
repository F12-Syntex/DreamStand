
package com.stand.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stand.GUI.ItemGui;
import com.stand.main.Stand;

public class Blocks extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	ItemGui gui = new ItemGui(player);
    	gui.open();

    }

    @Override

    public String name() {
        return "blocks";
    }

    @Override
    public String info() {
        return "Shows all the blocks.";
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
		return tabCompleter;
	}
	

}