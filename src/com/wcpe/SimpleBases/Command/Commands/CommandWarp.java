package com.wcpe.SimpleBases.Command.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandWarp implements CommandExecutor, TabCompleter {
	public CommandWarp(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lab, String[] arg) {
		if(!(boolean) a.getSetting().getWarp().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (arg.length == 0) {
				if (Utils.hasPermission(sender, "SimpleBases.warp")) {
					p.sendMessage(a.getLang().getMessage().get("warpHelp"));
				}

			} else if (arg.length == 1) {
				if (arg[0].equals("list")) {
					p.sendMessage("§aWarp列表:");
					for (Entry<String, Location> list : a.getData().getWarp().entrySet())
						p.sendMessage("§e" + list.getKey() + " ");
				}
			} else if (arg.length == 2) {
				if (arg[0].equals("add") && arg[1] != null) {
					if (!Utils.hasPermission(p, "SimpleBases.warp.add")) {
						return false;
					}
					if (!a.getData().getWarp().containsKey(arg[1])) {
						a.getData().getWarp().put(arg[1], p.getLocation());
						p.sendMessage(a.getLang().getMessage().get("addwarpFinish").replaceAll("%warp%", arg[1]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("warpExistence").replaceAll("%warp%", arg[1]));
					}
				} else if (arg[0].equals("del") && arg[1] != null) {
					if (!Utils.hasPermission(p, "SimpleBases.warp.del")) {
						return false;
					}
					if (a.getData().getWarp().containsKey(arg[1])) {
						a.getData().getWarp().remove(arg[1]);
						p.sendMessage(a.getLang().getMessage().get("delwarpFinish").replaceAll("%warp%", arg[1]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("warpNoExistence").replaceAll("%warp%", arg[1]));
					}
				} else if (arg[0].equals("move") && arg[1] != null) {
					if (!Utils.hasPermission(p, "SimpleBases.warp.move")) {
						return false;
					}
					if (a.getData().getWarp().containsKey(arg[1])) {
						a.getData().getWarp().put(arg[1], p.getLocation());
						p.sendMessage(a.getLang().getMessage().get("moveWarpFinish").replaceAll("%warp%", arg[1]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("warpNoExistence").replaceAll("%warp%", arg[1]));
					}
				} else if (arg[0].equals("tp") && arg[1] != null) {
					if (!Utils.hasPermission(p, "SimpleBases.warp.tp")) {
						return false;
					}
					if (a.getData().getWarp().containsKey(arg[1])) {
						p.teleport(a.getData().getWarp().get(arg[1]));
						p.sendMessage(a.getLang().getMessage().get("tpWarpFinish").replaceAll("%warp%", arg[1]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("warpNoExistence").replaceAll("%warp%", arg[1]));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("warpHelp"));
				}
			} else if (arg.length == 3) {
				if (arg[0].equals("set") && arg[1] != null && arg[2] != null) {
					if (!Utils.hasPermission(p, "SimpleBases.warp.set")) {
						return false;
					}
					if (a.getData().getWarp().containsKey(arg[1])) {
						a.getData().getWarp().put(arg[2], a.getData().getWarp().get(arg[1]));
						a.getData().getWarp().remove(arg[1]);
						p.sendMessage(a.getLang().getMessage().get("setWarpFinish").replaceAll("%warp%", arg[1])
								.replaceAll("%warp2%", arg[2]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("warpNoExistence").replaceAll("%warp%", arg[1]));
					}
				}
			} else {
				p.sendMessage(a.getLang().getMessage().get("warpHelp"));
			}

		}
		return false;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lab, String[] args) {
		if (args.length == 1) {
			return Arrays.asList("list", "add", "del", "move", "tp");
		}else if (args.length == 2) {
			List<String> aa = new ArrayList<String>();
			for (Entry<String, Location> list : a.getData().getWarp().entrySet()) {
				aa.add(list.getKey());
			}
			aa.add("set");
			return aa;
		}
		return null;
	}

}
