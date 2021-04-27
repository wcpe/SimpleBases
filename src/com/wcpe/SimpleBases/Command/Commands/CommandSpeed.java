package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandSpeed implements CommandExecutor, TabCompleter {
	public CommandSpeed(Main a) {
		this.a = a;
	}
	private Main a;
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(boolean) a.getSetting().getSpeed().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 2) {
				String[] pd = a.getLang().getMessage().get("speedFinish").split(";");
				if (args[0].equalsIgnoreCase("walk")) {
					if (Utils.hasPermission(p, "SimpleBases.speed.walk")) {
						float speedwalk;
						try {
							speedwalk = Float.valueOf(args[1]);
							if (speedwalk > 1 || speedwalk < -1) {
								p.sendMessage(a.getLang().getMessage().get("speedWrong"));
								return true;
							}
							speedwalk = Float.parseFloat(args[1]);
						} catch (Exception e1) {
							p.sendMessage(a.getLang().getMessage().get("speedWrong"));
							return true;
						}
						p.setWalkSpeed(speedwalk);
						p.sendMessage(pd[0].replaceAll("%walk%", "" + p.getWalkSpeed()));
						return true;
					}
				} else if (args[0].equalsIgnoreCase("fly")) {
					if (Utils.hasPermission(p, "SimpleBases.speed.fly")) {
						float speedfly;
						try {
							speedfly = Float.valueOf(args[1]);
							if (speedfly > 1 || speedfly < -1) {
								p.sendMessage(a.getLang().getMessage().get("speedWrong"));
								return true;
							}

							speedfly = Float.parseFloat(args[1]);
						} catch (Exception e1) {
							p.sendMessage(a.getLang().getMessage().get("speedWrong"));
							return true;
						}
						p.setFlySpeed(speedfly);
						p.sendMessage(pd[1].replaceAll("%fly%", "" + p.getFlySpeed()));
						return true;
					}
				}
			} else {
				p.sendMessage(a.getLang().getMessage().get("speedWrong"));
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}