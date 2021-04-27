package com.wcpe.SimpleBases.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.wcpe.SimpleBases.Main;

import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {
	/**
	 * @author WCPE 获取格式化时间 yyyy/MM/dd/HH-mm-ss
	 **/
	public static String getTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HH-mm-ss"));
	}

	/**
	 * @author WCPE 转换时间
	 **/
	public static String formatDate(long date) {
		long sec = date / 1000L;
		if (sec < 60L)
			return sec + "秒";
		if (sec < 3600L)
			return (sec / 60L) + "分" + (sec % 60L) + "秒";
		if (sec < 86400L) {
			return (sec / 3600L) + "小时" + (sec % 3600L / 60L) + "分" + (sec % 3600L % 60L) + "秒";
		}
		return (sec / 86400L) + "天" + (sec % 86400L / 3600L) + "小时" + (sec % 86400L % 3600L / 60L) + "分 "
				+ (sec % 86400L % 60L) + "秒";
	}

	/**
	 * @author WCPE 冷却系统
	 **/
	private static HashMap<String, HashMap<String, Long>> Wait = new HashMap<>();

	public static boolean isCooling(Player a, int b, String c,boolean d) {
		if (Wait.get(c) == null) {
			Wait.put(c, new HashMap<String, Long>());
		}
		if (Wait.get(c).get(a.getName()) == null) {
			Wait.get(c).put(a.getName(), 0L);
		}
		if (System.currentTimeMillis() >= Wait.get(c).get(a.getName())) {
			Wait.get(c).put(a.getName(), System.currentTimeMillis() + b * 1000);
			if(d) {
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
					a.sendMessage(Main.getInstance().getLang().getMessage().get("waitFinish").replaceAll("%cmd%", c));
				}, b * 20);
			}
			return true;
		} else {
			a.sendMessage(Main.getInstance().getLang().getMessage().get("waitCooling"));
			return false;
		}
	}

	/**
	 * @author WCPE 判断是否为玩家
	 **/
	public static boolean isPlayer(CommandSender a) {
		if (a instanceof Player) {
			return true;
		}
		a.sendMessage(Main.getInstance().getLang().getMessage().get("noPlayer"));
		return false;
	}

	/**
	 * @author WCPE 判断玩家是否离线或者不存在
	 **/
	public static boolean isPlayer(String a) {
		try {
			Bukkit.getPlayer(a);
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * @author WCPE 判断是否有权限
	 **/
	public static boolean hasPermission(CommandSender a, String b) {
		if (a.hasPermission(b)) {
			return true;
		}
		a.sendMessage(Main.getInstance().getLang().getMessage().get("noPermission"));
		return false;
	}

	/**
	 * @author WCPE 判断是否有空值
	 **/
	@SuppressWarnings("unchecked")
	public static boolean isNull(Object a, Player b) {
		if (((HashMap<String, Object>) a).get(b.getName()) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author WCPE 倒计时系统
	 **/
	public static void CountDown(int b, Runnable c, Player p, String d) {
		new Thread(() -> {
			for (int t = b; t > 0; t--) {
				p.sendMessage(Main.getInstance().getLang().getMessage().get("CountDown").replaceAll("%t%", "" + t)
						.replaceAll("%cmd%", d));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Bukkit.getScheduler().runTask(Main.getInstance(), c);
		}).start();
	}

	/**
	 * @author WCPE 替换papi
	 * 
	 **/
	public static String replaceAllPapi(String a, boolean haspapi, Player b) {
		String message = a;
		if (haspapi) {
			message = PlaceholderAPI.setPlaceholders(b, a);
		}
		return message.replaceAll("%player%", b.getName());
	}

	/**
	 * @author WCPE 玩家执行指令
	 * @param a       指令列表
	 * @param hasPapi 是否启用Papi
	 * @param p       玩家
	 **/
	public static void executionCommands(List<String> a, boolean hasPapi, Player p) {
		for (String commands : a) {
			String pd[] = commands.split("]");
			if (pd[0].equals("[CMD")) {
				if (pd[0].equals("[CMD")) {
					pd[1] = pd[1].replaceAll("%player%", p.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), pd[1]);
				}
			} else if (pd[0].equals("[CHAT")) {
				if (pd[0].equals("[CHAT")) {
					if (hasPapi) {
						pd[1] = PlaceholderAPI.setPlaceholders(p, pd[1]);
					}
					p.chat(pd[1]);
				}
			} else if (pd[0].equals("[TITLE")) {
				if (pd[0].equals("[TITLE")) {
					if (hasPapi) {
						pd[1] = PlaceholderAPI.setPlaceholders(p, pd[1]);
						pd[2] = PlaceholderAPI.setPlaceholders(p, pd[2]);
					}
					p.sendTitle(pd[1], pd[2], Integer.valueOf(pd[3]), Integer.valueOf(pd[4]), Integer.valueOf(pd[5]));

				}
			} else if (pd[0].equals("[ACTION")) {
				if (pd[0].equals("[ACTION")) {
					if (hasPapi) {
						pd[1] = PlaceholderAPI.setPlaceholders(p, pd[1]);
					}
					Nms.sendAction(p, pd[1]);
				}
			} else if (pd[0].equals("[OP")) {
				if (pd[0].equals("[OP")) {
					boolean isop = p.isOp();
					try {
						p.setOp(true);
						if (hasPapi) {
							pd[1] = PlaceholderAPI.setPlaceholders(p.getPlayer(), pd[1]);
						}

						p.chat(pd[1]);
					} catch (Exception eee) {
					} finally {
						p.setOp(isop);
					}

				}
			} else if (pd[0].equals("[Bd")) {
				if (hasPapi) {
					pd[1] = PlaceholderAPI.setPlaceholders(p, pd[1]);
				}
				Bukkit.broadcastMessage(pd[1]);
			} else {
				if (hasPapi) {
					pd[0] = PlaceholderAPI.setPlaceholders(p, pd[0]);
				}
				p.sendMessage(pd[0]);
			}
		}
	}
}
