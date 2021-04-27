package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandCclear implements CommandExecutor, TabCompleter {
	public CommandCclear(Main a) {
		this.a = a;
	}
	private Main a;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getClear().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.hasPermission(sender, "SimpleBases.cclear")) {
			clear(sender);
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	private void clear(CommandSender p) {
		List<World> worlds = Bukkit.getWorlds();
		int a = 0;
		int b = 0;
		for (World wd : worlds) {
			List<Entity> entity = wd.getEntities();
			for (Entity st : entity) {
				if (st.getType() == EntityType.DROPPED_ITEM) {
					a++;
					st.remove();
				}
				for (String ent : (List<String>) this.a.getSetting().getClear().get("ClearList")) {
					if (st.getType() == EntityType.valueOf(ent.toUpperCase())) {
						b++;
						st.remove();
					}
				}
			}
		}
		p.sendMessage(this.a.getLang().getMessage().get("clearDropMob").replaceAll("%drop%", "" + a)
				.replaceAll("%mobs%", "" + b));
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}
