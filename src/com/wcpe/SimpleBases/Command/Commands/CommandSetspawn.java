package com.wcpe.SimpleBases.Command.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

import java.util.List;

public class CommandSetspawn implements CommandExecutor, TabCompleter {
	public CommandSetspawn(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getSpawn().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.setspawn")) {
				if (args.length == 0) {
					a.getData().setSpawn( p.getLocation());
					p.sendMessage(a.getLang().getMessage().get("setSpawnFinish"));
				} else {
					p.sendMessage(a.getLang().getMessage().get("setSpawnWrong"));
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
