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

public class CommandBack implements CommandExecutor, TabCompleter {
	public CommandBack(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(boolean) a.getSetting().getBack().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.back")) {
				if (args.length == 0) {
					if (Utils.isNull(a.getData().getBack(), p)) {
						if (Utils.isCooling(p, (int) a.getSetting().getBack().get("Cooling"), "Back", true)) {
							Utils.CountDown((int) a.getSetting().getBack().get("CountDown"), () -> {
								Location loc = a.getData().getBack().get(p.getName());
								p.getPlayer().teleport(loc);
								p.sendMessage(a.getLang().getMessage().get("teleportFinish"));
							}, p, "Back");
						}
					} else {
						p.sendMessage(a.getLang().getMessage().get("noBack"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("backWrong"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}