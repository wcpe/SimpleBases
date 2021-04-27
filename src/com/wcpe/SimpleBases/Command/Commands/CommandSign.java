package com.wcpe.SimpleBases.Command.Commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.InterFace.SignInventory;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandSign implements CommandExecutor, Listener {
	public CommandSign(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lab, String[] args) {
		if (!(boolean) a.getSetting().getSign().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.sign")) {
				openQd(p);
			}
		}
		return false;
	}

	private Inventory inv;
	static List<ItemStack> itemlist = new ArrayList<ItemStack>();

	@SuppressWarnings("unchecked")
	public void openQd(Player p) {
		inv = Bukkit.createInventory(new SignInventory(), 45, ((String) a.getSetting().getSign().get("Title")).replaceAll("&", "§"));
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int year = aCalendar.get(Calendar.YEAR);// 年份
		int month = aCalendar.get(Calendar.MONTH) + 1;// 月份
		int day = aCalendar.get(Calendar.DATE);
		if (a.getData().getSign().get(p.getName()) == null) {
			a.getData().getSign().put(p.getName(), new HashMap<String, Boolean>());
		}
		for (String all : getDayListOfMonth()) {
			String[] pd = all.replaceAll("&", "§").split(";");
			String type = null;
			List<String> lore = null;
			boolean b = a.getData().getSign().get(p.getName()).get(year + "/" + month + "/" + pd[0]) == null ? false
					: a.getData().getSign().get(p.getName()).get(year + "/" + month + "/" + pd[0]);
			if (b) {
				type = (String) a.getSetting().getSign().get("AfterSignType");
				lore = (List<String>) a.getSetting().getSign().get("AfterSignLore");
			} else if (Integer.valueOf(pd[0]) < day) {
				type = (String) a.getSetting().getSign().get("oldSignType");
				lore = (List<String>) a.getSetting().getSign().get("oldSignLore");
			} else {
				type = (String) a.getSetting().getSign().get("BeforeSignType");
				lore = (List<String>) a.getSetting().getSign().get("BeforeSignLore");
			}

			int d = 0;
			for (String c : lore) {
				lore.set(d++, c.replaceAll("&", "§"));
			}
			ItemStack item = new ItemStack(Material.valueOf(type.toUpperCase()));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(pd[1]);
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.addItem(item);
			itemlist.add(item);
		}

		ItemStack tip = new ItemStack(
				Material.valueOf((((String) a.getSetting().getSign().get("RightDownTipType")).toUpperCase())));
		ItemMeta tipMeta = tip.getItemMeta();
		tipMeta.setDisplayName(((String) a.getSetting().getSign().get("RightDownTipName")).replaceAll("&", "§"));
		List<String> asList = ((List<String>) a.getSetting().getSign().get("RightDownTipLore"));
		int d = 0;
		for (String c : asList) {
			asList.set(d++, c.replaceAll("&", "§"));
		}
		tipMeta.setLore(asList);
		tip.setItemMeta(tipMeta);
		inv.setItem(44, tip);
		p.openInventory(inv);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void dis(InventoryClickEvent e) {
		try {
			if (e.getInventory().getHolder() instanceof SignInventory) {
				e.setCancelled(true);
				if (!(e.getSlotType().equals(SlotType.OUTSIDE))) {
					Boolean boolean1 = a.getData().getSign().get(e.getWhoClicked().getName())
							.get(Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR) + "/"
									+ (Calendar.getInstance(Locale.CHINA).get(Calendar.MONTH) + 1) + "/"
									+ Calendar.getInstance(Locale.CHINA).get(Calendar.DATE));
					if (e.getSlot() == Calendar.getInstance(Locale.CHINA).get(Calendar.DATE) - 1) {
						if (boolean1 != null && boolean1) { return; }
						a.getData().getSign().get(e.getWhoClicked().getName())
								.put(Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR) + "/"
										+ (Calendar.getInstance(Locale.CHINA).get(Calendar.MONTH) + 1) + "/"
										+ Calendar.getInstance(Locale.CHINA).get(Calendar.DATE), true);
						ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
						itemMeta.setLore((List<String>) a.getSetting().getSign().get("AfterSignLore"));
						e.getCurrentItem().setItemMeta(itemMeta);
						e.getCurrentItem().setType(Material
								.valueOf((((String) a.getSetting().getSign().get("AfterSignType")).toUpperCase())));
						Utils.executionCommands((List<String>) a.getSetting().getSign().get("DailyReward"), a.hasPapi,
								(Player) e.getWhoClicked());
					}
				}
			}
		} catch (java.lang.NullPointerException ee) {
		}
	}

	public List<String> getDayListOfMonth() {
		List<String> list = new ArrayList<String>();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int year = aCalendar.get(Calendar.YEAR);// 年份
		int month = aCalendar.get(Calendar.MONTH) + 1;// 月份
		int day = aCalendar.getActualMaximum(Calendar.DATE);
		for (int i = 1; i <= day; i++) {
			String aDate = i + ";" + ((String) a.getSetting().getSign().get("dataFormat")).replaceAll("%y%", "" + year)
					.replaceAll("%m%", "" + month).replaceAll("%d%", "" + i);
			list.add(aDate);
		}
		return list;
	}

}
