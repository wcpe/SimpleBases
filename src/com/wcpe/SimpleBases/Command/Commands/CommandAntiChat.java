package com.wcpe.SimpleBases.Command.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandAntiChat implements CommandExecutor, TabCompleter {
	public CommandAntiChat(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(boolean) a.getSetting().getChatFormat().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.antichat")) {
				if (a.getData().getAntichat().get("AntiChated") == null) {
					a.getData().getAntichat().put("AntiChated", new HashMap<>());
				}
				if (a.getData().getAntichat().get("AntiChat") == null) {
					a.getData().getAntichat().put("AntiChat", new HashMap<>());
				}
				HashMap<String, List<String>> ha = a.getData().getAntichat().get("AntiChated");// 玩家名 - 屏蔽该玩家的玩家
				HashMap<String, List<String>> hah = a.getData().getAntichat().get("AntiChat");// 玩家名 - 该玩家屏蔽的玩家
				if (args.length == 1 && args[0] != null) {
					if (!ha.containsKey(args[0])) {
						ha.put(args[0], new ArrayList<String>());
					}
					if (!hah.containsKey(p.getName())) {
						hah.put(p.getName(), new ArrayList<String>());
					}
					if (ha.get(args[0]).contains(p.getName()) && hah.get(p.getName()).contains(args[0])) {
						ha.get(args[0]).remove(p.getName());
						hah.get(p.getName()).remove(args[0]);
						p.sendMessage(a.getLang().getMessage().get("antichatRemove").replaceAll("%player%", args[0]));
					} else {
						ha.get(args[0]).add(p.getName());
						hah.get(p.getName()).add(args[0]);
						p.sendMessage(a.getLang().getMessage().get("antichatAdd").replaceAll("%player%", args[0]));
					}
				} else if (args.length == 2) {
					if ("list".equals(args[0]) && "me".equals(args[1])) {
						List<String> list = hah.get(p.getName());
						p.sendMessage(a.getLang().getMessage().get("antichatList").replaceAll("%list%",
								(list == null ? "无" : list.toString())));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("antichatHelp"));
				}

			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {

		return null;
	}

}
