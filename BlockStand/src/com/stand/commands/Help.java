
package com.stand.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stand.main.Stand;
import com.stand.utils.MessageUtils;

public class Help extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	if(args.length <= 1) {
    		MessageUtils.sendHelp(player);
    		return;
    	}
    	
    	SubCommand command = Stand.getInstance().CommandManager.get(args[1]);
    	
    	if(command == null) {
    		MessageUtils.sendMessage(player, Stand.getInstance().configManager.messages.invalid_help_command.replace("%command%", args[1]));
    		return;
    	}
    	
    	for(String i : Stand.getInstance().configManager.messages.help_format) {
    		MessageUtils.sendRawMessage(player, i.replace("%command%", args[1]).replace("%description%", command.info()).replace("%permission%", command.permission()).replace("%prefix%", Stand.getInstance().configManager.messages.prefix));
    	}
    	
    }

    @Override

    public String name() {
        return "help";
    }

    @Override
    public String info() {
        return "displays the help command";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Stand.getInstance().configManager.permissions.basic;
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