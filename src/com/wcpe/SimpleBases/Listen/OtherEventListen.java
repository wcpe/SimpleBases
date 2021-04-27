package com.wcpe.SimpleBases.Listen;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.wcpe.SimpleBases.Main;

public class OtherEventListen implements Listener {
	@SuppressWarnings("unchecked")
	public OtherEventListen(Main a) {
		this.a = a;
		Lore = (List<String>) a.getSetting().getMenu().get("Lore");
		Name = (String) a.getSetting().getMenu().get("Name");
		Type = (String) a.getSetting().getMenu().get("Type");
		Slot = (int) a.getSetting().getMenu().get("Slot");
		Menu = new ItemStack(Material.valueOf(Type));
		im = Menu.getItemMeta();
		im.setDisplayName(Name);
		im.setLore(Lore);
		Menu.setItemMeta(im);
	}

	private Main a;
	List<String> Lore;
	String Name;
	String Type;
	int Slot;
	public static ItemStack Menu;
	ItemMeta im;

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		if ((boolean) a.getSetting().getMenu().get("Enable")) {
			if (e.getPlayer().getInventory().getItem(Slot) == null) {
				e.getPlayer().getInventory().setItem(Slot, Menu);
			}
		}
	}

	@EventHandler
	public void Discard(PlayerDropItemEvent e) {
		if ((boolean) a.getSetting().getMenu().get("Enable")) {
			if (e.getItemDrop().getItemStack().equals(Menu)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void move(InventoryClickEvent e) {
		if ((boolean) a.getSetting().getMenu().get("Enable")) {
			try {
				if (!(e.getSlotType().equals(SlotType.OUTSIDE))) {
					if (e.getCurrentItem().equals(Menu)) {
						e.setCancelled(true);
					}
				}
				if (e.getHotbarButton() == Slot) {
					e.setCancelled(true);
				}
			} catch (java.lang.NullPointerException ee) {

			}
		}
	}
}
