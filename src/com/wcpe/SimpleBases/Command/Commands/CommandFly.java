package com.wcpe.SimpleBases.Command.Commands;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandFly implements CommandExecutor,TabCompleter{
	public CommandFly(Main a) {
		this.a = a;
	}
	private Main a;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getFly().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.fly")) {
				if (args.length == 0) {
					String[] pd = a.getLang().getMessage().get("flySwitch").split(";");
					if (p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage(pd[1]);
					} else {
						p.setAllowFlight(true);
						p.sendMessage(pd[0]);
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("flyWrong"));
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