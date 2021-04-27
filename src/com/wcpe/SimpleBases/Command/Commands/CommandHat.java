package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Listen.OtherEventListen;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandHat implements CommandExecutor,TabCompleter{
	public CommandHat(Main a) {
		this.a = a;
	}
	private Main a;
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getHat().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			if (Utils.hasPermission(sender, "SimpleBases.hat")) {
				Player p = (Player) sender;
				if(args.length == 0) {
					PlayerInventory inv = p.getInventory();
					ItemStack held = inv.getItemInMainHand();
					ItemStack helm = inv.getHelmet();
					if (held.getAmount() == 1 && held.getType() != Material.AIR && held != OtherEventListen.Menu) {
						inv.setHelmet(held);
						inv.setItemInMainHand(helm);
						p.updateInventory();
						p.sendMessage(a.getLang().getMessage().get("hatSwitch"));
					} else {
						p.sendMessage(a.getLang().getMessage().get("hatNumber"));
					}
				}else {
					p.sendMessage(a.getLang().getMessage().get("hatWrong"));
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