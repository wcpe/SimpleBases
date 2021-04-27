package com.wcpe.SimpleBases.Command.Commands;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

public class CommandloadData implements CommandExecutor, TabCompleter {
	public CommandloadData(Main a) {
		this.a = a;
	}

	private Main a;
	@SuppressWarnings("serial")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (Utils.hasPermission(sender, "SimpleBases.loaddata")) {
			List<String> list = Arrays.asList(a.getDataFolder().list());
			File file = new File(a.getDataFolder()+"/olddata");
			if(!file.exists()) {
				file.mkdirs();
			}
			if (list.contains("Spawn.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取Spawn~");
				File Spawn = new File(a.getDataFolder() , "Spawn.yml");
				YamlConfiguration spawn = YamlConfiguration.loadConfiguration(Spawn);
				a.getData().setSpawn(new Location(Bukkit.getWorld(spawn.getString("World.Name")), spawn.getDouble("World.X"), spawn.getDouble("World.Y"),spawn.getDouble("World.Z"), Float.valueOf(spawn.getString("World.Yaw")), Float.valueOf(spawn.getString("World.Pitch"))));
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入Spawn数据完毕  耗时§e"+(end-start)+"§aMs~");
				Spawn.renameTo(new File(a.getDataFolder()+"/olddata/"+Spawn.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 Spawn.yml文件 跳过");
			}
			if (list.contains("Back.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取Back~");
				File Back = new File(a.getDataFolder() , "Back.yml");
				YamlConfiguration back = YamlConfiguration.loadConfiguration(Back);
				for(String st:back.getKeys(false)) {
					a.getData().getBack().put(st, new Location(Bukkit.getWorld(back.getString(st+".World")), back.getDouble(st+".X"), back.getDouble(st+".Y"), back.getDouble(st+".Z"),Float.valueOf(back.getString(st+".Yaw")),Float.valueOf(back.getString(st+".Pitch"))));
				}
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入Home数据完毕 共读取§e"+back.getKeys(false).size()+"§a个玩家数据 耗时§e"+(end-start)+"§aMs~");
				Back.renameTo(new File(a.getDataFolder()+"/olddata/"+Back.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 Back.yml文件 跳过");
			}
			if (list.contains("Home.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取Home~");
				File Home = new File(a.getDataFolder() , "Home.yml");
				YamlConfiguration home = YamlConfiguration.loadConfiguration(Home);
				for(String st:home.getKeys(false)) {
					HashMap<String,Location> hashMap = a.getData().getHome().get(st);
					if(hashMap == null) {
						a.getData().getHome().put(st, new HashMap<String, Location>() {
							{
								put("home", new Location(Bukkit.getWorld(home.getString(st+".World")), home.getDouble(st+".X"), home.getDouble(st+".Y"), home.getDouble(st+".Z")));
							}
						});
					}else {
						a.getData().getHome().get(st).put("home", new Location(Bukkit.getWorld(home.getString(st+".World")), home.getDouble(st+".X"), home.getDouble(st+".Y"), home.getDouble(st+".Z")));
					}
				}
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入Home数据完毕 共读取§e"+home.getKeys(false).size()+"§a个玩家数据 耗时§e"+(end-start)+"§aMs~");
				Home.renameTo(new File(a.getDataFolder()+"/olddata/"+Home.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 Home.yml文件 跳过");
			}
			if (list.contains("PlayerOnline.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取PlayerOnline~");
				File Pm = new File(a.getDataFolder() , "PlayerOnline.yml");
				YamlConfiguration pm = YamlConfiguration.loadConfiguration(Pm);
				for(String st:pm.getConfigurationSection("PlayerOnlineTime").getKeys(false)) {
					if(Bukkit.getOfflinePlayer(UUID.fromString(st)) != null) {
						a.getData().getPm().put(Bukkit.getOfflinePlayer(UUID.fromString(st)).getName(), pm.getInt("PlayerOnlineTime."+st));
					}
				}
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入PlayerOnline数据完毕 共读取§e"+pm.getConfigurationSection("PlayerOnlineTime").getKeys(false).size()+"§a个玩家数据 耗时§e"+(end-start)+"§aMs~");
				Pm.renameTo(new File(a.getDataFolder()+"/olddata/"+Pm.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 PlayerOnlineTime.yml文件 跳过");
			}
			if (list.contains("FirstJoinTimer.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取FirstJoinTimer~");
				File FirstJoinTimer = new File(a.getDataFolder() , "FirstJoinTimer.yml");
				YamlConfiguration firstjointimer = YamlConfiguration.loadConfiguration(FirstJoinTimer);
				for(String st:firstjointimer.getStringList("Player")) {
						a.getData().getFirstJoin().put(st, true);
				}
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入FirstJoinTimer数据完毕 共读取§e"+firstjointimer.getStringList("Player").size()+"§a个玩家数据 耗时§e"+(end-start)+"§aMs~");
				FirstJoinTimer.renameTo(new File(a.getDataFolder()+"/olddata/"+FirstJoinTimer.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 FirstJoinTimer.yml文件 跳过");
			}
			if (list.contains("Warp.yml")) {
				long start = System.currentTimeMillis();
				sender.sendMessage("§a开始读取Warp~");
				File Warp = new File(a.getDataFolder() , "Warp.yml");
				YamlConfiguration warp = YamlConfiguration.loadConfiguration(Warp);
				for(String st:warp.getConfigurationSection("Warp").getKeys(false)) {
						a.getData().getWarp().put(st, new Location(Bukkit.getWorld(warp.getString("Warp."+st+".World")), warp.getDouble("Warp."+st+".X"), warp.getDouble("Warp."+st+".Y"), warp.getDouble("Warp."+st+".Z"), Float.valueOf(warp.getString("Warp."+st+".Yaw")) , Float.valueOf(warp.getString("Warp."+st+".Yaw"))));
				}
				long end = System.currentTimeMillis();
				sender.sendMessage("§b导入Warp数据完毕 共读取§e"+warp.getConfigurationSection("Warp").getKeys(false).size()+"§a个数据 耗时§e"+(end-start)+"§aMs~");
				Warp.renameTo(new File(a.getDataFolder()+"/olddata/"+Warp.getName()));
			}else {
				sender.sendMessage("§c找不到旧版本 Warp.yml文件 跳过");
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		return null;
	}

}
