package com.wcpe.SimpleBases.Listen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Nms;
import com.wcpe.SimpleBases.Utils.Utils;

public class PlayerEventListen implements Listener {
	public PlayerEventListen(Main a) {
		this.a = a;
	}

	private Main a;

	/**
	 * @author WCPE
	 * @param e 异步聊天
	 **/
	@EventHandler
	public void Chat(AsyncPlayerChatEvent e) {
		String chat = e.getMessage();
		if ((boolean) a.getSetting().getAtPlayer().get("Enable")) {

			int aa = chat.indexOf((String) a.getSetting().getAtPlayer().get("CheckAt"));
			if (aa != -1) {
				String t = chat.substring(aa + 1);
				if (chat.contains((String) a.getSetting().getAtPlayer().get("CheckAtAll"))) {
					try {
						e.getPlayer()
								.sendMessage(((String) a.getSetting().getAtPlayer().get("AtAll")).replaceAll("&", "§"));
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (a.getData().getPb().get(p.getName())) {
								continue;
							}
							p.sendMessage(((String) a.getSetting().getAtPlayer().get("Ated")).replaceAll("&", "§")
									.replaceAll("%player%", e.getPlayer().getName()).replaceAll("&", "§"));
							String[] title = ((String) a.getSetting().getAtPlayer().get("AtedTitle"))
									.replaceAll("&", "§").replaceAll("%player%", e.getPlayer().getName()).split(";");
							p.sendTitle(title[0], title[1], Integer.valueOf(title[2]), Integer.valueOf(title[3]),
									Integer.valueOf(title[4]));
						}
					} catch (java.lang.NullPointerException e1) {
					}
				} else {
					try {
						Player p = Bukkit.getPlayer(t);
						if (!a.getData().getPb().get(p.getName())) {
							e.getPlayer().sendMessage(((String) a.getSetting().getAtPlayer().get("At"))
									.replaceAll("&", "§").replaceAll("%player%", p.getName()));
							String[] title = ((String) a.getSetting().getAtPlayer().get("AtTitle")).replaceAll("&", "§")
									.replaceAll("%player%", p.getName()).split(";");
							e.getPlayer().sendTitle(title[0], title[1], Integer.valueOf(title[2]),
									Integer.valueOf(title[3]), Integer.valueOf(title[4]));
							if (p.isOnline()) {
								p.sendMessage(((String) a.getSetting().getAtPlayer().get("Ated"))
										.replaceAll("%player%", e.getPlayer().getName()).replaceAll("&", "§"));
								String[] title2 = ((String) a.getSetting().getAtPlayer().get("AtedTitle"))
										.replaceAll("&", "§").replaceAll("%player%", e.getPlayer().getName())
										.split(";");
								p.sendTitle(title2[0], title2[1], Integer.valueOf(title2[2]),
										Integer.valueOf(title2[3]), Integer.valueOf(title2[4]));
							} else {
								e.getPlayer().sendMessage(a.getLang().getMessage().get("playerOffline"));
								e.getPlayer().sendTitle("", a.getLang().getMessage().get("playerOffline"), 10, 70, 20);
							}
						} else {
							e.getPlayer().sendMessage(
									((String) a.getSetting().getAtPlayer().get("ShieldOpen")).replaceAll("&", "§"));
						}
					} catch (NullPointerException e1) {
						e.getPlayer().sendMessage(a.getLang().getMessage().get("playerOffline"));
						e.getPlayer().sendTitle("", a.getLang().getMessage().get("playerOffline"), 10, 70, 20);
					}
				}
			}
		}

		
	}

	/**
	 * @author WCPE
	 * @param e 玩家传送事件
	 **/
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == (TeleportCause.COMMAND) || (e.getCause() == TeleportCause.PLUGIN)) {
			addBackLoc(e.getPlayer());
		}
	}

	public void addBackLoc(Player p) {
		a.getData().getBack().put(p.getName(), p.getLocation());
	}

	@EventHandler
	public void Death(PlayerDeathEvent e) {
		addBackLoc(e.getEntity());
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		/**
		 * @author WCPE
		 *  Firstjoin
		 **/
		if ((boolean) a.getSetting().getFirstJoinCommands().get("Enable")) {
			if (a.getData().getFirstJoin().get(p.getName()) == null) {
				Utils.executionCommands((List<String>) a.getSetting().getFirstJoinCommands().get("Commands"), a.hasPapi, p);
				a.getData().getFirstJoin().put(p.getName(), true);
			}
		}

		/**
		 * @author WCPE
		 *  初始化屏蔽
		 **/
		if (a.getData().getPb().get(p.getName()) == null) {
			a.getData().getPb().put(p.getName(), false);
		}

		/**
		 * @author WCPE
		 *  join消息
		 **/
		if ((boolean) a.getSetting().getJoinQuitMessage().get("Enable")) {
			if (p.isOp()) {
				e.setJoinMessage(
						Utils.replaceAllPapi((String) a.getSetting().getJoinQuitMessage().get("OpJoin"), a.hasPapi, p));
			} else {
				e.setJoinMessage(Utils.replaceAllPapi((String) a.getSetting().getJoinQuitMessage().get("NoOpJoin"),
						a.hasPapi, p));
			}
		}
		HashMap<String, Object> title = (HashMap<String, Object>) a.getSetting().getJoinSendMessage().get("Title");
		HashMap<String, Object> message = (HashMap<String, Object>) a.getSetting().getJoinSendMessage().get("Message");
		HashMap<String, Object> action = (HashMap<String, Object>) a.getSetting().getJoinSendMessage().get("Actionbar");
		if ((boolean) title.get("Enable")) {
			p.sendTitle((String) title.get("main"), (String) title.get("sub"), (int) title.get("fadein"),
					(int) title.get("fadeout"), (int) (title.get("stay")));
		}
		if ((boolean) message.get("Enable")) {
			for (String st : (List<String>) message.get("Send")) {
				p.sendMessage(st);
			}
		}
		if ((boolean) action.get("Enable")) {
			Nms.sendAction(p, (String) action.get("Send"));
		}

		/**
		 * @author WCPE
		 *  记录joinserver
		 **/
		if ((boolean) a.getSetting().getTponline().get("Enable")) {
			HashMap<String, Location> hashMap = a.getData().getPlayerOnline().get(p.getName());
			if (hashMap == null) {
				a.getData().getPlayerOnline().put(p.getName(), new HashMap<String, Location>() {
					{
						put(Utils.getTime(), p.getLocation());
					}
				});
			} else {
				if (hashMap.size() > (int) a.getSetting().getTponline().get("SaveNumber")) {
					List<String> list = new ArrayList<String>(hashMap.keySet());
					Collections.sort(list);
					hashMap.remove(list.get(0));
				}
				hashMap.put(Utils.getTime(), p.getLocation());
			}
		}
	}

	@SuppressWarnings("serial")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if ((boolean) a.getSetting().getJoinQuitMessage().get("Enable")) {
			if (p.isOp()) {
				e.setQuitMessage(
						Utils.replaceAllPapi((String) a.getSetting().getJoinQuitMessage().get("OpQuit"), a.hasPapi, p));
			} else {
				e.setQuitMessage(Utils.replaceAllPapi((String) a.getSetting().getJoinQuitMessage().get("NoOpQuit"),
						a.hasPapi, p));
			}
		}
		/**
		 * @author WCPE
		 *  记录quitserver
		 **/
		if ((boolean) a.getSetting().getTpoffline().get("Enable")) {
			HashMap<String, Location> hashMap = a.getData().getPlayerOffline().get(p.getName());
			if (hashMap == null) {
				a.getData().getPlayerOffline().put(p.getName(), new HashMap<String, Location>() {
					{
						put(Utils.getTime(), p.getLocation());
					}
				});
			} else {
				if (hashMap.size() > (int) a.getSetting().getTpoffline().get("SaveNumber")) {
					List<String> list = new ArrayList<String>(hashMap.keySet());
					Collections.sort(list);
					hashMap.remove(list.get(0));
				}
				hashMap.put(Utils.getTime(), p.getLocation());
			}
		}
	}

}
