package com.wcpe.SimpleBases.Command.Commands;

import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandPm implements CommandExecutor, TabCompleter {
	public CommandPm(Main a) {
		this.a = a;
	}
	private Main a;
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(!(boolean) a.getSetting().getPlaycmd().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			if (Utils.hasPermission(p, "SimpleBases.pm")) {
				int s = a.getData().getPm().get(p.getName());
				if (args.length == 0) {
					String pmtype = (String) a.getSetting().getPlayerGameTime().get("Type");
					p.sendMessage((a.getLang().getMessage().get("playerGameTimeCheck")+pmtype).replaceAll("%time%", "" + s));
				} else if (args.length == 2) {
					if (args[0].equals("dh") && args[1] != null) {
						int ss;
						try {
							ss = Integer.valueOf(args[1]);
							if (ss > s || ss < 0) {
								String pmtype = (String) a.getSetting().getPlayerGameTime().get("Type");
								p.sendMessage(a.getLang().getMessage().get("playerGameTimeInsufficient")
										.replaceAll("%time%", s + pmtype));
								return true;
							}
						} catch (Exception e1) {
							p.sendMessage(a.getLang().getMessage().get("playerGameTimeWrong"));
							return true;
						}
						int cha = s - ss;
						a.getData().getPm().put(p.getName(), cha);
						List<String> listcommand = (List<String>) a.getSetting().getPlayerGameTime().get("Command");
						List<String> listmessage = (List<String>) a.getSetting().getPlayerGameTime().get("Message");
						for(String cd:listcommand) {
							cd = cd.replaceAll("%player%", p.getName());
							cd = cd.replaceAll("%pmdh%", String.valueOf(ss));
							cd = cd.replaceAll("%pmsy%", String.valueOf(cha));
							try {
								String sourceArray = cd.substring(cd.indexOf("<")+1, cd.indexOf(">"));
								int returns = 0;
								try {
									returns = (int)new ScriptEngineManager().getEngineByName("JavaScript").eval(sourceArray);
								}catch(NullPointerException e) {
									returns = Integer.valueOf(sourceArray);
								}
								sourceArray = "<"+sourceArray+">";
								cd = cd.replace(sourceArray, String.valueOf(returns));
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cd);
							} catch (ScriptException e) {
								p.sendMessage("§4请检查配置中表达式是否正确");
								System.out.println("§4请检查配置中表达式是否正确");
							}
						}
						for(String mg:listmessage) {
							mg = mg.replaceAll("%player%", p.getName());
							mg = mg.replaceAll("%pmdh%", String.valueOf(ss));
							mg = mg.replaceAll("%pmsy%", String.valueOf(cha));
							p.sendMessage(mg);
						}
						
					} else {
						p.sendMessage(a.getLang().getMessage().get("playerGameTimeWrong"));
					}
				} else {
					p.sendMessage(a.getLang().getMessage().get("playerGameTimeWrong"));
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
