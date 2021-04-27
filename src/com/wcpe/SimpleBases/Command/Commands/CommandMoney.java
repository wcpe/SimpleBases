package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandMoney implements CommandExecutor, TabCompleter {
	public CommandMoney(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(boolean) a.getSetting().getWarp().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}

		if (args.length == 0) {
			if(Utils.hasPermission(sender, "SimpleBases.money")) {
				if (Utils.isPlayer(sender)) {
					Player p = (Player) sender;
					a.getEconomy().getBalance(p);
				}
			}
		} else if (args.length == 1) {

		}else {
			
		}

		
		return false;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		return null;
	}

}
