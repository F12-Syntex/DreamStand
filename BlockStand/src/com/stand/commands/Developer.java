
package com.stand.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stand.GUI.PagedGeneratedGUI;
import com.stand.GUI.TestGeneratedGui;
import com.stand.main.Stand;

public class Developer extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    
		player.closeInventory();
		PagedGeneratedGUI gui = new TestGeneratedGui(player);
		gui.open(1);
    
    }

    @Override

    public String name() {
        return "developer";
    }

    @Override
    public String info() {
        return "testing command";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return Stand.getInstance().configManager.permissions.reload;	
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		return tabCompleter;
	}
	

}