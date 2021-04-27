package com.wcpe.SimpleBases.Command.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandTpoffline implements CommandExecutor, TabCompleter {
	public CommandTpoffline(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lab, String[] args) {
		if(!(boolean) a.getSetting().getTpoffline().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.tpoffline")) {
				if (args.length == 2 && args[0] != null && args[1] != null) {
					HashMap<String, Location> hashMap = a.getData().getPlayerOffline().get(args[0]) != null ? a.getData().getPlayerOffline().get(args[0]) : new HashMap<String, Location>();
					if (hashMap == null) {
						p.sendMessage(a.getLang().getMessage().get("tpNoExistence"));
						return false;
					} else {
						p.teleport(hashMap.get(args[1]));
						p.sendMessage(a.getLang().getMessage().get("tpofflineFinish"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("tpofflineWrong"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args.length == 1) {
			return new ArrayList<String>(a.getData().getPlayerOffline().keySet());
		} else if (args.length == 2) {
			HashMap<String, Location> hashMap = a.getData().getPlayerOffline().get(args[0]);
			if (hashMap == null) {
				return null;
			} else {
				return new ArrayList<String>(hashMap.keySet());
			}
		}
		return null;
	}
}
