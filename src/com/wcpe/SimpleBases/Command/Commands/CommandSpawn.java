package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandSpawn implements CommandExecutor, TabCompleter {
	public CommandSpawn(Main a) {
		this.a = a;
	}

	private Main a;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(boolean) a.getSetting().getSpawn().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.spawn")) {
				if (args.length == 0) {
					if (a.getData().getSpawn() != null) {
						if (Utils.isCooling(p, (int) a.getSetting().getSpawn().get("Cooling"), "Spawn", true)) {
							Utils.CountDown((int) a.getSetting().getSpawn().get("CountDown"), () -> {
								Location loc = a.getData().getSpawn();
								p.teleport(loc);
								p.sendMessage(a.getLang().getMessage().get("tpSpawnFinish"));
							}, p, "Spawn");
						}
					} else {
						p.sendMessage(a.getLang().getMessage().get("noSpawn"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("tpSpawnWrong"));
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}