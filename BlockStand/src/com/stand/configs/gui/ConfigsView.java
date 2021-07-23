package com.stand.configs.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.stand.GUI.GenerateItem;
import com.stand.GUI.PagedItem;
import com.stand.GUI.SpecialItem;
import com.stand.config.Config;
import com.stand.main.Stand;

public class ConfigsView extends ConfigGUI {

	public ConfigsView(Player player, ConfigGUI back, ConfigGUI front) {
		super(player, back, front, null);
		// TODO Auto-generated constructor stub
		
		if(back != null) {
			back.setFront(this);
		}
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Configs";
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Stand.getInstance().configManager.permissions.basic;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_LEVER_CLICK;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PagedItem> Contents() {
		
		List<PagedItem> items = new ArrayList<PagedItem>();
		
		for(Config i : Stand.getInstance().configManager.config) {
		
			List<String> lore = new ArrayList<String>();
			
			for(String o : i.getConfiguration().getKeys(false)) {
				lore.add("&7 - &6" + o);
				
			}
			
			
			ItemStack object = new ItemStack(Material.valueOf(Stand.getInstance().configManager.configs.Sections));
			
			ItemStack item = GenerateItem.getItem("&7" + i.getName(), object, lore);
			
			items.add(new PagedItem(item, () -> {
				player.closeInventory();
				ConfigGUI gui = new ConfigSpecific(player, i, this, null);
				gui.open();
			}));
			
		}
		
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		// TODO Auto-generated method stub
		return new ArrayList<SpecialItem>();
	}


}
