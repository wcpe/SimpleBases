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

public class CommandDelhome implements CommandExecutor, TabCompleter {
	public CommandDelhome(Main a) {
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
			if (Utils.hasPermission(p, "SimpleBases.delhome")) {
				if (args.length == 1) {
					if (args[0] != null) {
						HashMap<String, Location> hashMap = a.getData().getHome().get(p.getName());
						if (hashMap.get(args[0]) == null) {
							p.sendMessage(
									a.getLang().getMessage().get("homeNoExistence").replaceAll("%home%", args[0]));
						} else {
							a.getData().getHome().get(p.getName()).remove(args[0]);
							p.sendMessage(a.getLang().getMessage().get("delHomeFinish").replaceAll("%home%", args[0]));
						}
					} else {
						p.sendMessage(a.getLang().getMessage().get("delHomeWrong"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("delHomeWrong"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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
