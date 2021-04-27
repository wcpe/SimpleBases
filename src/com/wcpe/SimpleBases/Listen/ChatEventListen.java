package com.wcpe.SimpleBases.Listen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Obj.ChatFormatObj;
import com.wcpe.SimpleBases.Obj.ChatObj;
import com.wcpe.SimpleBases.Utils.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatEventListen implements Listener {
	public ChatEventListen(Main a) {
		this.a = a;
		cmd = a.getServer().getConsoleSender();
	}

	private Main a;

	private ConsoleCommandSender cmd;

	@SuppressWarnings("all")
	@EventHandler
	public void m(PlayerCommandPreprocessEvent e) {
		if ((boolean) a.getSetting().getChatFormat().get("Enable")) {
			String message = e.getMessage();
			e.setCancelled(true);
			List<ChatObj> chat = (List<ChatObj>) a.getSetting().getChatFormat().get("Chat");
			HashMap<String, ChatFormatObj> chatformat = (HashMap<String, ChatFormatObj>) a.getSetting().getChatFormat()
					.get("Format");
			int lastweight = -1;
			ChatObj c = null;
			for (ChatObj cc : chat) {
				if (!e.getPlayer().hasPermission(cc.getPermission())) {
					continue;
				}
				if (cc.getWeight() < lastweight) {
					continue;
				}
				lastweight = cc.getWeight();
				c = cc;
			}
			if (!c.isIgnoreCool()) {
				if (!Utils.isCooling(e.getPlayer(), c.getChatcool(), "聊天",
						(boolean) a.getSetting().getChatFormat().get("CoolFinishTipEnable"))) {
					return;
				}
			}
			if (!c.isIgnoreLength()) {
				if (message.length() > c.getChatlength()) {
					e.getPlayer().sendMessage(c.getChatlengthMessage());
					return;
				}
			}
			if (!c.isIgnoreReplaceAll()) {
				for (String s : c.getReplaceAll()) {
					String[] ss = s.split(";");
					message = message.replaceAll(ss[0], ss[1]);
				}
			}
			Bukkit.dispatchCommand(e.getPlayer(), message.substring(1));
		}
	}

	@SuppressWarnings("all")
	@EventHandler
	public void s(AsyncPlayerChatEvent e) {
		/**
		 * @author WCPE chat消息
		 **/
		if ((boolean) a.getSetting().getChatFormat().get("Enable")) {
			String message = e.getMessage();
			e.setCancelled(true);
			cmd.sendMessage(e.getFormat().replaceAll("%1\\$s", e.getPlayer().getName()).replaceAll("%2\\$s", message));
			List<ChatObj> chat = (List<ChatObj>) a.getSetting().getChatFormat().get("Chat");
			HashMap<String, ChatFormatObj> chatformat = (HashMap<String, ChatFormatObj>) a.getSetting().getChatFormat()
					.get("Format");
			int lastweight = -1;
			ChatObj c = null;
			for (ChatObj cc : chat) {
				if (!e.getPlayer().hasPermission(cc.getPermission())) {
					continue;
				}
				if (cc.getWeight() < lastweight) {
					continue;
				}
				lastweight = cc.getWeight();
				c = cc;
			}
			if (!c.isIgnoreCool()) {
				if (!Utils.isCooling(e.getPlayer(), c.getChatcool(), "聊天",
						(boolean) a.getSetting().getChatFormat().get("CoolFinishTipEnable"))) {
					return;
				}
			}
			if (!c.isIgnoreLength()) {
				if (message.length() > c.getChatlength()) {
					e.getPlayer().sendMessage(c.getChatlengthMessage());
					return;
				}
			}
			if (!c.isIgnoreReplaceAll()) {
				for (String s : c.getReplaceAll()) {
					String[] ss = s.split(";");
					message = message.replaceAll(ss[0], ss[1]);
				}
			}
			if (a.getData().getAntichat().get("AntiChated") == null) {
				a.getData().getAntichat().put("AntiChated", new HashMap<>());
			}
			HashMap<String, List<String>> ha = a.getData().getAntichat().get("AntiChated");
			List<String> list = ha.get(e.getPlayer().getName()) == null ? new ArrayList<String>()
					: ha.get(e.getPlayer().getName());
			for (Player p : a.getServer().getOnlinePlayers()) {
				if (list.contains(p.getName())) {
					continue;
				}
				ComponentBuilder create = new ComponentBuilder("");
				Pattern pz = Pattern.compile("[^\\[\\]]+");
				Matcher m = pz.matcher(c.getFormat());
				while (m.find()) {
					String group = m.group();
					if (chatformat.containsKey(group)) {
						ChatFormatObj chatFormatObj = chatformat.get(group);
						String mess = chatFormatObj.getMessage();
						String command = chatFormatObj.getCommand();
						String lists = chatFormatObj.getList();
						try {
							mess = mess.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%", message);
							command = command.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%",
									message);
							lists = lists.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%", message);
						} catch (IllegalArgumentException ee) {
							mess = mess.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%", message);
							command = command.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%",
									message);
							lists = lists.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%chat%", message);
						}
						create.append(createComponent(mess, command, lists).create());
					}
				}
				sendMessage(p, create.create());
			}
		}
	}

	private ComponentBuilder createComponent(String message, String cmd, String list) {
		return new ComponentBuilder(message).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(list).create()));
	}

	private void sendMessage(Player p, BaseComponent[] create) {
		p.spigot().sendMessage(create);
	}

}
