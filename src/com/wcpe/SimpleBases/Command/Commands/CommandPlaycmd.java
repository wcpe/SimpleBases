package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandPlaycmd implements CommandExecutor, TabCompleter {
	public CommandPlaycmd(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getPlaycmd().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.hasPermission(sender, "SimpleBases.playcmd")) {
			playcmd(sender, args);
		}
		return false;
	}

	private void playcmd(CommandSender p, String[] arg) {
		int length = arg.length;
		if (length > 1) {
			try {
				Player pl = Bukkit.getPlayer(arg[0]);
				StringBuilder a = new StringBuilder();
				for (int t = 1; t < length; t++)
					a.append(arg[t] + " ");
				pl.chat(a.toString());
			} catch (NullPointerException e1) {
				p.sendMessage(a.getLang().getMessage().get("playerOffline"));
			}
		} else {
			p.sendMessage(a.getLang().getMessage().get("playcmdWrong"));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}
