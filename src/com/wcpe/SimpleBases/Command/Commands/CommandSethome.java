package com.wcpe.SimpleBases.Command.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

import java.util.HashMap;
import java.util.List;

public class CommandSethome implements CommandExecutor, TabCompleter {
	public CommandSethome(Main a) {
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
			if (Utils.hasPermission(p, "SimpleBases.sethome")) {
				if (args.length == 1) {
					if (args[0] != null) {
						if (!a.getData().getHome().containsKey(p.getName())) {
							a.getData().getHome().put(p.getName(), new HashMap<String, Location>());
						}
						if (a.getData().getHome().get(p.getName())
								.size() >= (int) a.getSetting().getHome().get("Number")) {
							p.sendMessage(a.getLang().getMessage().get("setHomeLimit"));
							return false;
						}
						a.getData().getHome().get(p.getName()).put(args[0], p.getLocation());
						p.sendMessage(a.getLang().getMessage().get("setHomeFinish").replaceAll("%home%", args[0]));
					} else {
						p.sendMessage(a.getLang().getMessage().get("setHomeWrong"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("setHomeWrong"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}
}
