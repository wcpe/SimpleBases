package com.wcpe.SimpleBases.Command.Commands;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.sun.management.OperatingSystemMXBean;
import com.wcpe.SimpleBases.Main;
import com.wcpe.SimpleBases.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

@SuppressWarnings({ "restriction", "unused" })
public class CommandGc implements CommandExecutor, TabCompleter {
	public CommandGc(Main a) {
		this.a = a;
	}

	private Main a;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(boolean) a.getSetting().getGc().get("Enable")) {
			sender.sendMessage(a.getLang().getMessage().get("noEnable"));
			return false;
		}
		if (Utils.hasPermission(sender, "SimpleBases.gc")) {
			gc(sender);
		}
		return false;
	}

	public void gc(CommandSender p) {
		long JvmFree, JvmUse, JvmTotal;
		JvmFree = JvmTotal = JvmUse = 0;
		int byteToMb = 1024 * 1024;
		Runtime rt = Runtime.getRuntime();
		JvmTotal = rt.totalMemory() / byteToMb;
		JvmFree = rt.freeMemory() / byteToMb;
		JvmUse = JvmTotal - JvmFree;
		int Cpunumber = rt.availableProcessors();
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		List<String> list = ManagementFactory.getRuntimeMXBean().getInputArguments();
		ThreadInfo[] dumpAllThreads = ManagementFactory.getThreadMXBean().dumpAllThreads(false, false);
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		long physicalFree = osmxb.getFreePhysicalMemorySize() / byteToMb;
		long physicalTotal = osmxb.getTotalPhysicalMemorySize() / byteToMb;
		long physicalUse = physicalTotal - physicalFree;
		p.sendMessage("§9§l======================================");
		p.sendMessage("§a操作系统: §e" + os + " " + version + "(" + arch + ")");
		p.sendMessage("§a操作系统总物理内存: §e" + physicalTotal + " §aMB");
		p.sendMessage("§a操作系统已用物理内存: §e" + physicalUse + " §aMB");
		p.sendMessage("§a操作系统空闲物理内存: §e" + physicalFree + " §aMB");
		p.sendMessage("§9§l======================================");
		String t = "§e";
		for (String st : list) {
			t = t + " " + st;
		}
		p.sendMessage("§a服务器启动参数: §e" + t);
		p.sendMessage("§9§l======================================");
		p.sendMessage("§a运行时间: §e"
				+ Utils.formatDate(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()));
		if (Bukkit.getServer().getVersion().contains("Paper")) {
			double[] tps = Bukkit.getTPS();
			p.sendMessage("§aTPS:§e" + String.format("%.2f", tps[0]) + "," + String.format("%.2f", tps[1]) + ","
					+ String.format("%.2f", tps[2]));
		}
		p.sendMessage("§aJvm已用内存: §e" + JvmUse + " §aMB");
		p.sendMessage("§aJvm空闲内存: §e" + JvmFree + " §aMB");
		p.sendMessage("§aJvm总计内存: §e" + JvmTotal + " §aMB");
		p.sendMessage("§aJvm使用处理器个数: §e" + Cpunumber);
		p.sendMessage("§a线程: §e" + dumpAllThreads.length);
		p.sendMessage("§9§l======================================");
		for (World wd : Bukkit.getWorlds()) {
			int tileEntities = 0;
			try {
				for (Chunk chunk : wd.getLoadedChunks()) {
					tileEntities += chunk.getTileEntities().length;
				}
			} catch (ClassCastException ex) {
				Bukkit.getLogger().log(Level.SEVERE, "无法读取 错误的世界" + wd, ex);
			}
			p.sendMessage("§a世界: §e" + wd.getName() + "§a - §e" + wd.getEnvironment().toString() + " §a区块数量: §e"
					+ wd.getLoadedChunks().length + " §a实体数量: §e" + Integer.valueOf(wd.getEntities().size())
					+ " §aTiles: §e" + Integer.valueOf(tileEntities) + " §a玩家数量: §e"
					+ Integer.valueOf(wd.getPlayers().size()));
			HashMap<String, Integer> worldentity = new HashMap<>();
			wd.getEntities().forEach((s) -> {
				if (!worldentity.containsKey("§e" + s.getType().toString() + "§a")) {
					worldentity.put("§e" + s.getType().toString() + "§a", 1);
				} else {
					worldentity.put("§e" + s.getType().toString() + "§a", worldentity.get("§e" + s.getType().toString() + "§a") + 1);
				}
			});
			List<String> players = new ArrayList<>();
			wd.getPlayers().forEach((s) -> {
				players.add(s.getName());
			});
			p.sendMessage("§a详细实体: " + worldentity.toString().replaceAll("=", " =§e ").replaceAll(", ", "§a, ").replaceAll("}", "§a}"));
			p.sendMessage("§a详细玩家: " + players.toString().replaceAll("\\[", "\\[§e").replaceAll(", ", "§a, §e").replaceAll("\\]", "§a\\]"));
		}
		p.sendMessage("§9§l=============================================");
		File[] files = File.listRoots();
		for (File file : files) {
			String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024) + "§aG";
			String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "§aG";
			String use = new DecimalFormat("#.#")
					.format((file.getTotalSpace() - file.getFreeSpace()) * 1.0 / 1024 / 1024 / 1024) + "G";
			String path = file.getPath();
			p.sendMessage("§e" + path + "§a总大小: §e" + total + "§a;已用空间: §e" + use + "§a;剩余空间: §e" + free);
		}
		p.sendMessage("§9§l=============================================");
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
}
