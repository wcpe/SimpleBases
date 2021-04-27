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

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandTpa implements CommandExecutor, TabCompleter {
	public CommandTpa(Main a) {
		this.a = a;
	}

	private Main a;

	public static Boolean Send = false;
	public static Player SenderPlayer;
	public static Player ReceivePlayer;

	public static Boolean SendHere = false;
	public static Player SenderHerePlayer;
	public static Player ReceiveHerePlayer;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(boolean) a.getSetting().getTpa().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.isPlayer(sender)) {
			Player p = (Player) sender;
			switch (cmd.getName()) {
			case "tpa": {
				if (Utils.hasPermission(sender, "SimpleBases.tpa")) {
					if (args.length == 1) {
						sendTpa(p, args[0]);
					} else {
						p.sendMessage(a.getLang().getMessage().get("tpaWrong"));
					}
				}
				break;
			}
			case "tpahere": {
				if (Utils.hasPermission(sender, "SimpleBases.tpahere")) {
					if (args.length == 1) {
						sendHereTpa(p, args[0]);
					} else {
						p.sendMessage(a.getLang().getMessage().get("tpahereWrong"));
					}
				}
				break;
			}
			case "tpaccept": {
				if (Utils.hasPermission(sender, "SimpleBases.tpaccept")) {
					if (args.length == 0) {
						if (ReceivePlayer == p) {
							if (Send) {
								SenderPlayer.sendMessage(a.getLang().getMessage().get("tpAccept"));
								ReceivePlayer.sendMessage(a.getLang().getMessage().get("tpaAccept").replaceAll("%player%", SenderPlayer.getName()));
								Utils.CountDown((int) a.getSetting().getTpa().get("CountDown"), () -> {
									SenderPlayer.teleport(ReceivePlayer);
								}, ReceivePlayer, "Tpa");
								Send = false;
							} else {
								p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
							}
						} else if (ReceiveHerePlayer == p) {
							if (SendHere) {
								SenderHerePlayer.sendMessage(a.getLang().getMessage().get("tpAccept"));
								ReceiveHerePlayer.sendMessage(a.getLang().getMessage().get("tpaAccept").replaceAll("%player%", SenderHerePlayer.getName()));
								Utils.CountDown((int) a.getSetting().getTpa().get("CountDown"), () -> {
									ReceiveHerePlayer.teleport(SenderHerePlayer);
								}, ReceiveHerePlayer, "Tpa");
								SendHere = false;

							} else {
								p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
							}
						} else {
							p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
						}
					} else {
						p.sendMessage(a.getLang().getMessage().get("tpacceptWrong"));
					}
				}
				break;
			}
			case "tpdeny": {
				if (Utils.hasPermission(sender, "SimpleBases.tpdeny")) {
					if (args.length == 0) {
						if (ReceivePlayer == p) {
							if (Send) {
								SenderPlayer.sendMessage(a.getLang().getMessage().get("tpDeny"));
								ReceivePlayer.sendMessage(a.getLang().getMessage().get("tpaDeny").replaceAll("%player%", SenderPlayer.getName()));
								Send = false;
							} else {
								p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
							}
						} else if (ReceiveHerePlayer == p) {
							if (SendHere) {
								SenderHerePlayer.sendMessage(a.getLang().getMessage().get("tpDeny"));
								ReceiveHerePlayer.sendMessage(a.getLang().getMessage().get("tpaDeny").replaceAll("%player%", SenderHerePlayer.getName()));
								SendHere = false;

							} else {
								p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
							}
						} else {
							p.sendMessage(a.getLang().getMessage().get("tpaNoRequest"));
						}
					} else {
						p.sendMessage(a.getLang().getMessage().get("tpdenyWrong"));
					}
				}
				break;
			}
			}
		}
		return false;
	}

	private void sendTpa(Player a, String b) {
		if (!Utils.isCooling(a, (int) this.a.getSetting().getTpa().get("Cooling"), "Tpa",true)) {
			return;
		}
		Player receivePlayer = null;
		try {
			receivePlayer = Bukkit.getPlayer(b);
		} catch (NullPointerException e) {
			a.sendMessage(this.a.getLang().getMessage().get("playerOffline"));
			return;
		}
		receivePlayer.sendMessage(this.a.getLang().getMessage().get("tpaRequest").replaceAll("%player%", a.getName()));

		BaseComponent[] create = new ComponentBuilder(this.a.getLang().getMessage().get("tpacceptButton"))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder(this.a.getLang().getMessage().get("tpacceptButton")).create()))
				.append("     ")
				.append(new ComponentBuilder(this.a.getLang().getMessage().get("tpdenyButton"))
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder(this.a.getLang().getMessage().get("tpdenyButton")).create()))
						.create())
				.create();

		receivePlayer.spigot().sendMessage(create);

		a.sendMessage(this.a.getLang().getMessage().get("tpaFinish").replaceAll("%player%", receivePlayer.getName()));

		Send = true;
		SenderPlayer = a;
		ReceivePlayer = receivePlayer;
	}

	private void sendHereTpa(Player a, String b) {
		if (!Utils.isCooling(a, (int) this.a.getSetting().getTpa().get("Cooling"), "Tpa",true)) {
			return;
		}
		Player receivePlayer = null;
		try {
			receivePlayer = Bukkit.getPlayer(b);
		} catch (NullPointerException e) {
			a.sendMessage(this.a.getLang().getMessage().get("playerOffline"));
			return;
		}
		receivePlayer
				.sendMessage(this.a.getLang().getMessage().get("tpaHereRequest").replaceAll("%player%", a.getName()));

		BaseComponent[] create = new ComponentBuilder(this.a.getLang().getMessage().get("tpacceptButton"))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder(this.a.getLang().getMessage().get("tpacceptButton")).create()))
				.append("     ")
				.append(new ComponentBuilder(this.a.getLang().getMessage().get("tpdenyButton"))
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder(this.a.getLang().getMessage().get("tpdenyButton")).create()))
						.create())
				.create();

		receivePlayer.spigot().sendMessage(create);

		a.sendMessage(this.a.getLang().getMessage().get("tpaFinish").replaceAll("%player%", receivePlayer.getName()));

		SendHere = true;
		SenderHerePlayer = a;
		ReceiveHerePlayer = receivePlayer;
	}
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}