package com.wcpe.SimpleBases.Command.Commands;


import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandGm implements CommandExecutor,TabCompleter {
	public CommandGm(Main a) {
		this.a = a;
	}
	private Main a;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getGamemode().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 1) {
				String[] pd = a.getLang().getMessage().get("gmSwitch").split(";");
				switch (args[0]) {
				case "0":
					if (Utils.hasPermission(p, "SimpleBases.gm.0")) {
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(pd[0]);
					}
					break;
				case "1":
					if (Utils.hasPermission(p, "SimpleBases.gm.1")) {
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(pd[1]);
					}
					break;
				case "2":
					if (Utils.hasPermission(p, "SimpleBases.gm.2")) {
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(pd[2]);
					}
					break;
				case "3":
					if (Utils.hasPermission(p, "SimpleBases.gm.3")) {
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(pd[3]);
					}
					break;
				}
			}else {
				sender.sendMessage(a.getLang().getMessage().get("gmWrong"));
			}
		}
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}

}
