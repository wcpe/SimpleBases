package com.wcpe.SimpleBases.Command.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;


public class CommandZimiao implements CommandExecutor {
	Boolean zimiaoof = false;
	int zimiaoTaskId;

	@Override
	public boolean onCommand(CommandSender player, Command cmd, String arg2, String[] arg) {
		if (cmd.getName().equalsIgnoreCase("zimiao")) {
			if (player instanceof Player) {
				Player p = (Player) player;
				if (p.hasPermission("SimpleBases.zimiao")) {
					if (arg.length == 1) {
						if (arg[0].equals("on")) {
							if (!zimiaoof) {
								BukkitTask zimiao = Bukkit.getScheduler().runTaskTimer(
										Bukkit.getPluginManager().getPlugin("SimpleBases"), new Runnable() {
											@Override
											public void run() {
												Location loc = new Location(Bukkit.getWorld("world"),
														p.getLocation().getX() + 0.5, p.getLocation().getY() + 2,
														p.getLocation().getZ() + 0.5);
												drawLine(loc, p.getTargetBlock(null, 100).getLocation());
											}
										}, 20L, 20L);
								zimiaoTaskId = zimiao.getTaskId();
								zimiaoof = true;
								p.sendMessage("§azimiao特效已经开启");
								return true;
							}
							if (zimiaoof) {
								Bukkit.getScheduler().cancelTask(zimiaoTaskId);
								zimiaoof = false;
								p.sendMessage("§czimiao特效已经关闭");
							}
						} else {
						}
					} else {
					}
				} else {
				}
			} else {
			}
		}
		return false;
	}

	public void drawLine(Location start, Location end) {
		if (start.getWorld() != end.getWorld()) {
			return;
		}
		Vector offsetUnit = end.clone().subtract(start).toVector();
		double times = offsetUnit.length() / 0.1;
		offsetUnit.multiply(1 / times);
		drawLine(start, offsetUnit, times);
	}

	public void drawLine(Location start, Vector offsetUnit, double times) {
		Location currentLoc = start.clone();
		if (times < 1) {
			times = 1;
		}
		for (; times > 0; times--) {
			currentLoc.getWorld().spawnParticle(Particle.FLAME, currentLoc, 1, 0, 0, 0, 0);
			currentLoc.add(offsetUnit);
		}
	}
}
