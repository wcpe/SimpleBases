package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandPb implements CommandExecutor,TabCompleter{
	public CommandPb(Main a) {
		this.a = a;
	}
	private Main a;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getAtPlayer().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if(Utils.isPlayer(sender)) {
			Player p = (Player)sender;
			if(Utils.hasPermission(p, "SimpleBases.pb")) {
				boolean b = a.getData().getPb().get(p.getName()) != null?a.getData().getPb().get(p.getName()):false;
				if(b) {
					a.getData().getPb().put(p.getName(), false);
					p.sendMessage(a.getLang().getMessage().get("offShield"));
				}else {
					a.getData().getPb().put(p.getName(), true);
					p.sendMessage(a.getLang().getMessage().get("onShield"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		return null;
	}


}
