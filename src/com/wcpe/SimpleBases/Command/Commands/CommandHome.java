package com.wcpe.SimpleBases.Command.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHome implements CommandExecutor, TabCompleter {
	public CommandHome(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(boolean) a.getSetting().getHome().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.home")) {
				if (args.length == 0) {
					HashMap<String, Location> hashMap = a.getData().getHome().get(p.getName());
					if (hashMap != null) {
						p.sendMessage("§a您当前的家§b:" + hashMap.keySet().toString());
					} else {
						p.sendMessage(a.getLang().getMessage().get("noHome"));
					}
				} else if (args.length == 1) {
					if (args[0] != null) {
						if (Utils.isNull(a.getData().getHome(), p)) {
							if (a.getData().getHome().get(p.getName()).get(args[0]) != null) {
								if (Utils.isCooling(p, (int) a.getSetting().getHome().get("Cooling"), "Home", true)) {
									Utils.CountDown((int) a.getSetting().getHome().get("CountDown"), () -> {
										Location loc = a.getData().getHome().get(p.getName()).get(args[0]);
										p.teleport(loc);
										p.sendMessage(a.getLang().getMessage().get("tpHomeFinish").replaceAll("%home%",
												args[0]));
									}, p, "Home");

								}
							} else {
								p.sendMessage(
										a.getLang().getMessage().get("homeNoExistence").replaceAll("%home%", args[0]));
							}
						} else {
							p.sendMessage(a.getLang().getMessage().get("noHome"));
						}
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("tpHomeWrong"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args.length == 1) {
			HashMap<String, Location> hashMap = a.getData().getHome().get(sender.getName());
			if (hashMap != null) {
				return new ArrayList<String>(hashMap.keySet());
			} else {
				return null;
			}
		}
		return null;
	}
}
