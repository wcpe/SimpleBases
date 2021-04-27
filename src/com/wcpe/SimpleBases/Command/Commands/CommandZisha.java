package com.wcpe.SimpleBases.Command.Commands;


import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;


public class CommandZisha implements CommandExecutor,TabCompleter {
	public CommandZisha(Main a) {
		this.a = a;
	}
	private Main a;
	public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
		if(!(boolean) a.getSetting().getZisha().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if(Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if(Utils.hasPermission(sender, "SimpleBases.zisha")) {
				if(args.length == 0) {
					p.setHealth(0.0);
				}else {
					a.getLang().getMessage().get("zishaWrong");
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