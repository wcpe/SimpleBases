package com.wcpe.SimpleBases;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.wcpe.SimpleBases.Command.Commands.CommandAntiChat;
import com.wcpe.SimpleBases.Command.Commands.CommandBack;
import com.wcpe.SimpleBases.Command.Commands.CommandCclear;
import com.wcpe.SimpleBases.Command.Commands.CommandDelhome;
import com.wcpe.SimpleBases.Command.Commands.CommandFly;
import com.wcpe.SimpleBases.Command.Commands.CommandGc;
import com.wcpe.SimpleBases.Command.Commands.CommandGm;
import com.wcpe.SimpleBases.Command.Commands.CommandHat;
import com.wcpe.SimpleBases.Command.Commands.CommandHome;
import com.wcpe.SimpleBases.Command.Commands.CommandPb;
import com.wcpe.SimpleBases.Command.Commands.CommandPlaycmd;
import com.wcpe.SimpleBases.Command.Commands.CommandPm;
import com.wcpe.SimpleBases.Command.Commands.CommandSethome;
import com.wcpe.SimpleBases.Command.Commands.CommandSetspawn;
import com.wcpe.SimpleBases.Command.Commands.CommandSpawn;
import com.wcpe.SimpleBases.Command.Commands.CommandSpeed;
import com.wcpe.SimpleBases.Command.Commands.CommandTpa;
import com.wcpe.SimpleBases.Command.Commands.CommandTpoffline;
import com.wcpe.SimpleBases.Command.Commands.CommandTponline;
import com.wcpe.SimpleBases.Command.Commands.CommandWarp;
import com.wcpe.SimpleBases.Command.Commands.CommandZisha;
import com.wcpe.SimpleBases.Command.Commands.CommandloadData;
import com.wcpe.SimpleBases.Command.Commands.CommandSign;
import com.wcpe.SimpleBases.Listen.ChatEventListen;
import com.wcpe.SimpleBases.Listen.EntityEventListen;
import com.wcpe.SimpleBases.Listen.OtherEventListen;
import com.wcpe.SimpleBases.Listen.PlayerEventListen;
import com.wcpe.SimpleBases.Obj.ConfigObj;
import com.wcpe.SimpleBases.Obj.DataObj;
import com.wcpe.SimpleBases.Obj.MessageObj;
import com.wcpe.SimpleBases.Utils.PapiHook;
import com.wcpe.SimpleBases.Utils.Utils;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public final class Main extends JavaPlugin {
	public final Runnable updata = () -> {
		try {
			String version = UpCheck.upCheckVersion().getString("NewVersion");
			if (UpCheck.isLatestVersion()) {
				log("§4当前不是最新版本 " + "§a最新版本:§8§l" + version);
				List<String> stringList = UpCheck.upCheckVersion().getStringList("Version." + version + ".Updata");
				for (String st : stringList) {
					log(st.replaceAll("%version%", ""+Version));
				}
			} else {
				log("§a§lb§r§e(§0●§e'§8◡§e'§0●§e)§a§ld§r§e当前为最新版本 " + version + " §a§lb§r§e(￣▽￣)§a§ld§r§8§l");
			}
		} catch (IllegalThreadStateException e) {
			log("§4检查更新失败！！！");
		}
	};

	public void log(String log) {
		getServer().getConsoleSender().sendMessage("§a[§1Si§2mp§3le§4Ba§5se§6s§a]§r" + log);
	}

	public final static double Version = 1.2;

	public boolean hasPapi = false;
	private DataObj data;
	private ConfigObj setting;
	private MessageObj lang;

	private Economy economy = null;
	private Chat chat = null;
	private static Main Instance;

	public static Main getInstance() {
		return Instance;
	}

	public final void setSetting(ConfigObj setting) {
		this.setting = setting;
	}

	public final void setLang(MessageObj lang) {
		this.lang = lang;
	}

	public final void setData(DataObj data) {
		this.data = data;
	}

	public final MessageObj getLang() {
		return lang;
	}

	public final ConfigObj getSetting() {
		return setting;
	}

	public final DataObj getData() {
		return data;
	}

	public final Chat getChat() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			return chat;
		}
		return null;
	}

	public final Economy getEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			return economy;
		}
		return null;
	}

	@Override
	public void onEnable() {
		log("§1  _________.__               .__        __________                              ");
		log("§2 /   _____/|__| _____ ______ |  |   ____\\______   \\_____    ______ ____   ______");
		log("§3 \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\|    |  _/\\__  \\  /  ___// __ \\ /  ___/");
		log("§4 /        \\|  |  Y Y  \\  |_> >  |_\\  ___/|    |   \\ / __ \\_\\___ \\\\  ___/ \\___ \\ ");
		log("§5/_______  /|__|__|_|  /   __/|____/\\___  >______  /(____  /____  >\\___  >____  >");
		log("§6        \\/          \\/|__|             \\/       \\/      \\/     \\/     \\/     \\/");
		log("§7                            §aVersion: 1.2                                                              ");
		Instance = this;
		ConfigurationSerialization.registerClass(DataObj.class);
		new bStats(this, 6531);
		init();
		log("§aSimpleBases插件已装载完毕！");
	}

	@Override
	public void onDisable() {
		saveData();
		log("§1  _________.__               .__        __________                              ");
		log("§2 /   _____/|__| _____ ______ |  |   ____\\______   \\_____    ______ ____   ______");
		log("§3 \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\|    |  _/\\__  \\  /  ___// __ \\ /  ___/");
		log("§4 /        \\|  |  Y Y  \\  |_> >  |_\\  ___/|    |   \\ / __ \\_\\___ \\\\  ___/ \\___ \\ ");
		log("§5/_______  /|__|__|_|  /   __/|____/\\___  >______  /(____  /____  >\\___  >____  >");
		log("§6        \\/          \\/|__|             \\/       \\/      \\/     \\/     \\/     \\/");
		log("§7                            §aVersion: 1.2                                                              ");
		log("§cSimpleBases插件已卸载完毕！");
	}

	private void saveData() {
		File datafile = new File(this.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(datafile);
		data.set("data", this.data);
		try {
			data.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		saveDefaultYml();
		loadConfig();
		loadData();
		loadPapi();
		loadVault();
		registerCommand();
		registerListen();
		PlayerGameTime();
		timeUpCheck();
	}

	private void loadConfig() {
		setting = new ConfigLoad(this).getSetting();
		try {
			lang = new MessageObj("cn");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void loadData() {
		File datafile = new File(this.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(datafile);
		this.data = data.get("data") != null ? (DataObj) data.get("data") : new DataObj();
	}

	private void timeUpCheck() {
		if ((boolean) getSetting().getCheckVersion().get("Enable")) {
			Bukkit.getScheduler().runTaskTimer(this, () -> {
				new Thread(this.updata);
			}, 0, (long) getSetting().getCheckVersion().get("Time"));
		}
	}

	private void saveDefaultYml() {
		File Mess = new File(this.getDataFolder(), "Message.yml");
		if (!Mess.exists()) {
			this.saveResource("Message.yml", false);
		} else {
			File f = new File(this.getDataFolder(), "Message.yml");
			YamlConfiguration a = YamlConfiguration.loadConfiguration(f);

			InputStream resource = getResource("Message.yml");
			YamlConfiguration y = null;
			try {
				y = YamlConfiguration.loadConfiguration(new InputStreamReader(resource, "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			for (String b : y.getConfigurationSection("Message").getKeys(false)) {
				if (a.getString("Message." + b) == null) {
					a.set("Message." + b, y.getString("Message." + b));
					try {
						y.save(f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		reloadConfig();
		File config = new File(this.getDataFolder(), "config.yml");
		if (!config.exists()) {
			this.saveResource("config.yml", false);
		} else {
			File f = new File(this.getDataFolder(), "config.yml");
			YamlConfiguration a = YamlConfiguration.loadConfiguration(f);
			InputStream resource = getResource("config.yml");
			YamlConfiguration y = null;
			try {
				y = YamlConfiguration.loadConfiguration(new InputStreamReader(resource, "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			double version = y.getDouble("ConfigVersion");
			Double version2 = a.getDouble("ConfigVersion");
			if (version > version2) {
				File fi = new File(this.getDataFolder(), "config.yml");
				if (version2 == 0.0) {
					fi.renameTo(new File(this.getDataFolder(), "config_old_Version1.2↓.yml"));
				} else {
					fi.renameTo(new File(this.getDataFolder(), "config_old_" + version2 + ".yml"));
				}
				saveDefaultConfig();
			} else {
				Set<String> keys = a.getKeys(true);
				for (String b : y.getKeys(true)) {
					if (!keys.contains(b)) {
						this.getConfig().set(b, y.get(b));
					}
				}
				saveConfig();
			}
		}
	}

	private void PlayerGameTime() {
		long a = 0;
		String pmtype = (String) getSetting().getPlayerGameTime().get("Type");
		if (pmtype.equalsIgnoreCase("D")) {
			a = 20L * 60L * 60L * 24L;
		} else if (pmtype.equalsIgnoreCase("H")) {
			a = 20L * 60L * 60L;
		} else if (pmtype.equalsIgnoreCase("M")) {
			a = 20L * 60L;
		} else if (pmtype.equalsIgnoreCase("S")) {
			a = 20L;
		}
		if (a != 0) {
			Bukkit.getScheduler().runTaskTimer(this, () -> {
				for (Player p : Bukkit.getOnlinePlayers()) {
					int b = data.getPm().get(p.getName()) != null ? (data.getPm().get(p.getName()) + 1) : 0;
					data.getPm().put(p.getName(), b);
				}
			}, 0L, a);
		} else {
			log("§c在线奖励关闭 请检查4SimpleBases插件配置文件中在线时间Type");
		}
	}

	private void registerCommand() {
		Bukkit.getPluginCommand("simplebases").setExecutor(this);
		Bukkit.getPluginCommand("back").setExecutor(new CommandBack(this));
		Bukkit.getPluginCommand("cclear").setExecutor(new CommandCclear(this));
		Bukkit.getPluginCommand("gm").setExecutor(new CommandGm(this));
		Bukkit.getPluginCommand("fly").setExecutor(new CommandFly(this));
		Bukkit.getPluginCommand("gc").setExecutor(new CommandGc(this));
		Bukkit.getPluginCommand("hat").setExecutor(new CommandHat(this));
		Bukkit.getPluginCommand("home").setExecutor(new CommandHome(this));
		Bukkit.getPluginCommand("sethome").setExecutor(new CommandSethome(this));
		Bukkit.getPluginCommand("delhome").setExecutor(new CommandDelhome(this));
		Bukkit.getPluginCommand("spawn").setExecutor(new CommandSpawn(this));
		Bukkit.getPluginCommand("setspawn").setExecutor(new CommandSetspawn(this));
		Bukkit.getPluginCommand("playcmd").setExecutor(new CommandPlaycmd(this));
		Bukkit.getPluginCommand("pb").setExecutor(new CommandPb(this));
		Bukkit.getPluginCommand("tpa").setExecutor(new CommandTpa(this));
		Bukkit.getPluginCommand("tpahere").setExecutor(new CommandTpa(this));
		Bukkit.getPluginCommand("tpaccept").setExecutor(new CommandTpa(this));
		Bukkit.getPluginCommand("tpdeny").setExecutor(new CommandTpa(this));
		Bukkit.getPluginCommand("tpdeny").setExecutor(new CommandTpa(this));
		Bukkit.getPluginCommand("warp").setExecutor(new CommandWarp(this));
		Bukkit.getPluginCommand("speed").setExecutor(new CommandSpeed(this));
		Bukkit.getPluginCommand("pm").setExecutor(new CommandPm(this));
		Bukkit.getPluginCommand("sign").setExecutor(new CommandSign(this));
		Bukkit.getPluginCommand("tponline").setExecutor(new CommandTponline(this));
		Bukkit.getPluginCommand("tpoffline").setExecutor(new CommandTpoffline(this));
		Bukkit.getPluginCommand("loaddata").setExecutor(new CommandloadData(this));
		Bukkit.getPluginCommand("zisha").setExecutor(new CommandZisha(this));
		Bukkit.getPluginCommand("antichat").setExecutor(new CommandAntiChat(this));
	}

	private void registerListen() {
		Bukkit.getPluginManager().registerEvents(new PlayerEventListen(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityEventListen(this), this);
		Bukkit.getPluginManager().registerEvents(new OtherEventListen(this), this);
		Bukkit.getPluginManager().registerEvents(new CommandSign(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatEventListen(this), this);
	}

	private void loadPapi() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			log("§aPlaceholderAPI已安装！");
			new PapiHook(this).register();
			if (new PapiHook(this).reg()) {
				log("§aSimpleBases与PlaceholderAPI Hook变量成功！");
			} else {
				log("§cSimpleBases与PlaceholderAPI Hook变量失败！");
			}
			hasPapi = true;
		} else {
			log("§4您未安装PlaceholderAPI！");
			log("§4SimpleBases变量将无法加载！");
		}
	}

	private void loadVault() {
		try {
			if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
				log("§aVault已安装！");
				RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
						.getRegistration(net.milkbowl.vault.economy.Economy.class);
				economy = economyProvider.getProvider();
				RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager()
						.getRegistration(net.milkbowl.vault.chat.Chat.class);
				chat = rsp.getProvider();
			} else {
				log("§4您未安装Vault!");
			}
		} catch (java.lang.NullPointerException e) {
			log("§4您未安装Vault!");
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("updata")) {
				if (Utils.hasPermission(sender, "SimpleBases.updata")) {
					new Thread(updata).start();
					sender.sendMessage("§a[§bSimpleBases§a]§a正在检查是否有新版本...消息发送至后台");
					return false;
				} else {
					return false;
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (Utils.hasPermission(sender, "SimpleBases.reload")) {
					long start = System.currentTimeMillis();
					saveData();
					init();
					long end = System.currentTimeMillis();
					sender.sendMessage(getLang().getMessage().get("reload").replaceAll("%ms%", "" + (end - start)));
					return false;
				} else {
					return false;
				}
			}
		}
		sender.sendMessage("§aSimpleBases updata §e检查更新");
		sender.sendMessage("§aSimpleBases reload §e重载插件");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Arrays.asList(new String[] { "updata", "reload" });
	}

}