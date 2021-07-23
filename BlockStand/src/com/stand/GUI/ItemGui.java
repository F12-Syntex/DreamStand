package com.stand.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.stand.config.Settings;
import com.stand.main.Stand;
import com.stand.utils.Blocks;
import com.stand.utils.ComponentBuilder;
import com.stand.utils.MessageUtils;

public class ItemGui extends PagedGUI{

	public ItemGui(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cItems");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Stand.getInstance().configManager.permissions.admin;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.AMBIENT_CAVE;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0f;
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
		
		for(String mat : Stand.getInstance().configManager.settings.blocks) {
			
			ItemStack item = GenerateItem.getItem("&c" + Blocks.safeBlockNames(Material.valueOf(mat)), new ItemStack(Material.valueOf(mat)), ComponentBuilder.createLore("&7Left click to remove."));
			
			items.add(new PagedItem(item, ()->{

				Settings settings = Stand.getInstance().configManager.settings;
				
				List<String> blocks = settings.blocks;
				
				blocks.remove(mat);
				
				settings.getConfiguration().set("Settings.Blocks", blocks);
				settings.save();
				
				player.closeInventory();
				ItemGui gui = new ItemGui(player);
				gui.page = this.page;
				gui.open();
			
				
			}));
		}
		
		
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		// TODO Auto-generated method stub
		return new ArrayList<SpecialItem>();
	}

}
