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

public class Tx implements CommandExecutor{
	Boolean fkof = false;
	Boolean yxof = false;
	Boolean hrof = false;
	int fkTaskId;
	int yxTaskId;
	int hrTaskId;
	
	private double degree = 0;
	
	@Override
	public boolean onCommand(CommandSender player, Command cmd, String arg2, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("tx")) {
			if(player instanceof Player) {
				Player p = (Player) player;
				if(p.hasPermission("SimpleBases.tx")) {
					if(arg.length == 2) {
						if(arg[0].equals("fk") &&arg[1].equals("on")) {
							if(!fkof) {
								BukkitTask fk = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("SimpleBases"), new Runnable() {
									@Override
						    	    public void run() {
										for(int t = 10;t>0;t--) {
												Location locys = new Location(Bukkit.getWorld("world"), p.getLocation().getX()+t, p.getLocation().getY(), p.getLocation().getZ()+t);
												Location loczs = new Location(Bukkit.getWorld("world"), p.getLocation().getX()+t, p.getLocation().getY(), p.getLocation().getZ()-t);
												Location locyx = new Location(Bukkit.getWorld("world"), p.getLocation().getX()-t, p.getLocation().getY(), p.getLocation().getZ()+t);
												Location loczx = new Location(Bukkit.getWorld("world"), p.getLocation().getX()-t, p.getLocation().getY(), p.getLocation().getZ()-t);
												drawLine(loczs, locys);
												drawLine(locys, locyx);
												drawLine(locyx, loczx);
												drawLine(loczx, loczs);
										}
									}
								}, 20L, 20L);
								fkTaskId = fk.getTaskId();
								fkof = true;
								p.sendMessage("§afk特效已经开启");
								return true;
							}
							if(fkof) {
								Bukkit.getScheduler().cancelTask(fkTaskId);
								fkof = false;
								p.sendMessage("§cfk特效已经关闭");
							}
						}else if(arg[0].equals("yx") &&arg[1].equals("on")) {
							if(!yxof) {
								BukkitTask yx = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("SimpleBases"), new Runnable() {
									@Override
						    	    public void run() {
										Location playerLocation = p.getLocation();
								        double radians = Math.toRadians(degree);

								        Location playEffectLocation = playerLocation.clone().add(0.3 * Math.cos(radians), 2D, 0.3 * Math.sin(radians));
								        playEffectLocation.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, playEffectLocation, 10, 0, 0, 0, 0);

								        if (degree >= 360) {
								            degree = 0;
								        } else {
								            degree += 20;
								        }
									}
								}, 0L, 1L);
								yxTaskId = yx.getTaskId();
								yxof = true;
								p.sendMessage("§ayx特效已经开启");
								return true;
							}
							if(yxof) {
								Bukkit.getScheduler().cancelTask(yxTaskId);
								yxof = false;
								p.sendMessage("§cyx特效已经关闭");
							}
						}else if(arg[0].equals("hr") &&arg[1].equals("on")) {
							if(!hrof) {
								BukkitTask hr = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("SimpleBases"), new Runnable() {
									@Override
						    	    public void run() {
										double radius = 1D;
										for (int degree = 0; degree < 360; degree++) {
										        double radians = Math.toRadians(degree);
										        double x = radius * Math.cos(radians);
										        double z = radius * Math.sin(radians);
										        double y = Math.sin(radians);
										        Location loc1 = new Location(Bukkit.getWorld("world"), p.getLocation().getX()+x, p.getLocation().getY()+y+0.75, p.getLocation().getZ()+z);
										        loc1.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc1, 1, 0, 0, 0, 0);
										        loc1.subtract(x, y, z);
										}

										for (int degree = 0; degree < 360; degree++) {
										    double radians = Math.toRadians(degree);
										    double x = radius * Math.cos(radians);
										    double z = radius * Math.sin(radians);
										    double y = Math.sin(radians);
										    Location loc2 = new Location(Bukkit.getWorld("world"), p.getLocation().getX()-x, p.getLocation().getY()+y+0.75, p.getLocation().getZ()-z);
										    loc2.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc2, 1, 0, 0, 0, 0);
										    loc2.subtract(-x, y, -z);
										}
									}
								}, 20L, 20L);
								hrTaskId = hr.getTaskId();
								hrof = true;
								p.sendMessage("§ahr特效已经开启");
								return true;
							}
							if(hrof) {
								Bukkit.getScheduler().cancelTask(hrTaskId);
								hrof = false;
								p.sendMessage("§chr特效已经关闭");
							}
						}else {
						}
					}else {
					}
				}else {
				}
			}else {
			}
		}
		return false;
	}
	public static void drawLine(Location locA, Location locB) {
	    Vector vectorAB = locB.clone().subtract(locA).toVector();
	    double vectorLength = vectorAB.length();
	    vectorAB.normalize();
	    for (double i = 0; i < vectorLength; i += 0.1) {
	        Vector vector = vectorAB.clone().multiply(i);
	        locA.add(vector);
	        locA.getWorld().spawnParticle(Particle.FLAME, locA, 1, 0, 0, 0, 0);
	        locA.subtract(vector);
	    }
	}
}
