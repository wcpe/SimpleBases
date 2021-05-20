package top.wcpe.simplebases;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.sun.management.OperatingSystemMXBean;
import com.wcpe.SimpleBases.Utils.Utils;

import lombok.Getter;
import top.wcpe.simplebases.playerdata.PlayerData;
import top.wcpe.simplebases.playerdata.filedata.PlayerHome;
import top.wcpe.simplebases.playerdata.filedata.PlayerOnlineLocation;
import top.wcpe.wcpelib.bukkit.chat.ChatAcceptParameterManager;
import top.wcpe.wcpelib.bukkit.command.CommandPlus;
import top.wcpe.wcpelib.bukkit.command.entity.Command;
import top.wcpe.wcpelib.bukkit.command.entity.CommandArgument;
import top.wcpe.wcpelib.bukkit.command.intel.ExecuteComponentFunctional;
import top.wcpe.wcpelib.bukkit.command.intel.TabCompleterFunctional;
import top.wcpe.wcpelib.bukkit.inventory.InventoryManager;
import top.wcpe.wcpelib.bukkit.inventory.InventoryPlus;
import top.wcpe.wcpelib.bukkit.inventory.InventoryPlus.Builder;
import top.wcpe.wcpelib.bukkit.inventory.entity.Slot;
import top.wcpe.wcpelib.bukkit.itemstack.SkullManager;
import top.wcpe.wcpelib.bukkit.utils.NmsUtil;
import top.wcpe.wcpelib.common.utils.collector.ListUtil;
import top.wcpe.wcpelib.common.utils.datatime.OtherUtil;
import top.wcpe.wcpelib.common.utils.datatime.TimeUtil;
import top.wcpe.wcpelib.common.utils.math.Decimal;
import top.wcpe.wcpelib.common.utils.string.StringUtil;

/**
 * 基础指令类
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:12:17
 */
@SuppressWarnings("all")
public class SimpleBasesCommand {

	private final SimpleBases instance;

	public SimpleBasesCommand(SimpleBases instance) {
		this.instance = instance;
	}

	@Getter
	private final HashMap<String, Command> registerCommand = new HashMap<>();

	public SimpleBasesCommand buildCommand() {
		gcCommand();
		flyCommand();
		speedCommand();
		gmCommand();
		suicideCommand();
		hatCommand();
		setSpawnCommand();
		spawnCommand();
		backCommand();
		tpaCommand();
		tponlineCommand();
		CommandPlus commandPlus = new CommandPlus.Builder("SimpleBases", instance).aliases("sb").build();
		commandPlus.registerSubCommand(new Command.Builder("reload", "重载配置文件").executeComponent((sender, args) -> {
			instance.reloadConfig();
		}).tabCompleter((sender, args) -> {
			return null;
		}).build());
		for (Entry<String, Command> entry : getRegisterCommand().entrySet()) {
			commandPlus.registerSubCommand(entry.getValue());
		}
		commandPlus.registerThis();
		return this;
	}

	private void tponlineCommand() {
		registerMainCommand("tponline", "玩家上线位置记录", (sender, args) -> {
			PlayerData pd = SimpleBases.getSimpleBasesDataManager().getPlayerData(args[0]);
			if (pd == null) {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("playerNoExistent", "player:" + args[0]));
				return;
			}
			if ("".equals(args[1])) {
				InventoryManager onlineLocationInventoryManager = new InventoryManager();
				ConfigurationSection tponlineSetting = instance.getSetting("tponline")
						.getConfigurationSection("inventory");
				int temp = 0;
				List<List<PlayerOnlineLocation>> splitList = ListUtil.splitList(pd.listPlayerOnlineLocation(), 36);
				for (List<PlayerOnlineLocation> listPlayerOnlineLocation : splitList) {
					InventoryPlus iPlus = new InventoryPlus.Builder().disClickNullSlot(true).disDoubleClick(true)
							.disDrag(true).disClickPlayerGui(true).row(6)
							.title(StringUtil.replaceString(tponlineSetting.getString("title"), "player:" + args[0],
									"page:" + temp))
							.build();
					if (temp != 0) {
						iPlus.setSlot(0, new Slot.Builder().type(Material.ARROW).name("§c上一页").onClick((dto -> {
							InventoryClickEvent e = dto.getInventoryClickEvent();
							e.setCancelled(true);
							if (!(e.getWhoClicked() instanceof Player)) {
								return;
							}
							Player p = (Player) e.getWhoClicked();
							iPlus.getLastInventory().openThisInventory(p);
						})).build());
					}
					if (temp != splitList.size() - 1) {
						iPlus.setSlot(53, new Slot.Builder().type(Material.ARROW).name("§c下一页").onClick((dto -> {
							InventoryClickEvent e = dto.getInventoryClickEvent();
							e.setCancelled(true);
							if (!(e.getWhoClicked() instanceof Player)) {
								return;
							}
							Player p = (Player) e.getWhoClicked();
							iPlus.getNextInventory().openThisInventory(p);
						})).build());
					}
					temp++;
					for (int i = 0; i < listPlayerOnlineLocation.size(); i++) {
						PlayerOnlineLocation pol = listPlayerOnlineLocation.get(i);
						iPlus.setSlot(i + 9, new Slot.Builder(SkullManager.getSkullItemStack(pd.getName()))
								.name(StringUtil.replaceString(tponlineSetting.getString("name"),
										"time:" + TimeUtil.stampToTime(pol.getTimestamp())))
								.lore(ListUtil.replaceString(tponlineSetting.getStringList("lore"),
										"toTime:"
												+ TimeUtil.formatDate(System.currentTimeMillis() - pol.getTimestamp())))
								.onClick((dto) -> {
									InventoryClickEvent e = dto.getInventoryClickEvent();
									e.setCancelled(true);
									if (!(e.getWhoClicked() instanceof Player)) {
										return;
									}
									if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT) {
										Player p = (Player) e.getWhoClicked();
										Location loc = pol.getLocation();
										p.teleport(loc);
										p.sendMessage(SimpleBases.getMessageManager().getMessage("tponlineSuccess",
												"player:" + args[0], "time:" + TimeUtil.stampToTime(pol.getTimestamp()),
												"world:" + loc.getWorld().getName(),
												"x:" + Decimal.decimalFormat(loc.getX()),
												"y:" + Decimal.decimalFormat(loc.getY()),
												"z:" + Decimal.decimalFormat(loc.getZ())));
									}
									if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
									}
								}).build());
					}
					onlineLocationInventoryManager.put(iPlus.getRawInventory(), iPlus);
				}
				((Player) sender)
						.openInventory(onlineLocationInventoryManager.getFirstInventoryPlus().getRawInventory());
				return;
			}
			long parseLong = 0L;
			try {
				parseLong = Long.parseLong(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("pleaseInputCorrectTimestamp"));
				return;
			}
			Location loc = pd.getPlayerOnlineLocation(parseLong).getLocation();

			if (loc == null) {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("thisTimeNoRecord"));
				return;
			}
			((Player) sender).teleport(loc);
			sender.sendMessage(SimpleBases.getMessageManager().getMessage("tponlineSuccess", "player:" + args[0],
					"time:" + TimeUtil.stampToTime(parseLong), "world:" + loc.getWorld().getName(),
					"x:" + Decimal.decimalFormat(loc.getX()), "y:" + Decimal.decimalFormat(loc.getY()),
					"z:" + Decimal.decimalFormat(loc.getZ())));
		}, (sender, args) -> {
			if (args.length == 1) {
				return SimpleBases.getSimpleBasesDataManager().listPlayerName().stream()
						.filter(name -> name.startsWith(args[0])).collect(Collectors.toList());
			}
			if (args.length == 2) {
				PlayerData pd = SimpleBases.getSimpleBasesDataManager().getPlayerData(args[0]);
				if (pd != null) {
					return pd.listPlayerOnlineLocation().stream().map(PlayerOnlineLocation::getTimestamp)
							.filter(time -> String.valueOf(time).startsWith(args[1])).map(String::valueOf)
							.collect(Collectors.toList());
				}
			}
			return null;
		}, true, new CommandArgument.Builder("玩家").build(), new CommandArgument.Builder("时间戳").ignoreArg("").build());
	}

	private void tpaCommand() {
		registerMainCommand("tpa", "打开传送面板", (sender, args) -> {
			InventoryManager tpaInventoryManager = new InventoryManager();
			for (List<Player> listPlayer : ListUtil.splitList((List<Player>) instance.getServer().getOnlinePlayers(),
					36)) {
				InventoryPlus iPlus = new InventoryPlus.Builder().disClickNullSlot(true).disDoubleClick(true)
						.disDrag(true).disClickPlayerGui(true).row(6).title("§a传送面板").build();
				for (int i = 0; i < listPlayer.size(); i++) {
					Player p = listPlayer.get(i);
					iPlus.setSlot(i + 9, new Slot.Builder(SkullManager.getSkullItemStack(p.getName())).name(p.getName())
							.lore("s", "s").onClick((dto) -> {
								dto.getInventoryClickEvent().setCancelled(true);
							}).build());
				}
				tpaInventoryManager.put(iPlus.getRawInventory(), iPlus);
			}
			((Player) sender).openInventory(tpaInventoryManager.getFirstInventoryPlus().getRawInventory());
		}, (sender, args) -> {
			return null;
		}, true, new CommandArgument.Builder("死亡传送至此").ignoreArg("false").build(),
				new CommandArgument.Builder("玩家加入服务器传送至此").ignoreArg("false").build());
	}

	private void backCommand() {
		registerMainCommand("back", "返回上次传送地点", (sender, args) -> {
			long coolFinish = OtherUtil.isCoolFinish("SimpleBases:back");
			if (coolFinish != -1) {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("backCooling", "s:" + coolFinish));
				return;
			}
			ConfigurationSection setting = instance.getSetting("back");

			OtherUtil.putCoolTime("SimpleBases:back", setting.getInt("coolTime"));
			OtherUtil.putCountDownTask((m) -> {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("backCountDown", "s:" + m));
			}, () -> {
				Player p = (Player) sender;
				if (!p.isOnline()) {
					return;
				}
				Location location = PlayerBufferArea.getPlayerBackLocation().get(sender.getName());
				if (location == null) {
					sender.sendMessage(SimpleBases.getMessageManager().getMessage("backNotFind"));
					return;
				}
				p.teleport(location);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("backSuccess"));
			}, setting.getInt("countDownTime"));
		}, (sender, args) -> {
			return null;
		}, true, new CommandArgument.Builder("死亡传送至此").ignoreArg("false").build(),
				new CommandArgument.Builder("玩家加入服务器传送至此").ignoreArg("false").build());
	}

	private void particleCommand() {
		registerMainCommand("particle", "粒子特效", (sender, args) -> {
			Player p = (Player) sender;

		}, (sender, args) -> {
			return null;
		}, true, new CommandArgument.Builder("样式").build(), new CommandArgument.Builder("粒子").build());
	}

	private void spawnCommand() {
		registerMainCommand("spawn", "返回出生点", (sender, args) -> {
			long coolFinish = OtherUtil.isCoolFinish("SimpleBases:spawn");
			if (coolFinish != -1) {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("spawnCooling", "s:" + coolFinish));
				return;
			}
			ConfigurationSection setting = instance.getSetting("serverSpawn");

			OtherUtil.putCoolTime("SimpleBases:spawn", setting.getInt("coolTime"));
			OtherUtil.putCountDownTask((m) -> {
				sender.sendMessage(SimpleBases.getMessageManager().getMessage("spawnCountDown", "s:" + m));
			}, () -> {
				Player p = (Player) sender;
				if (!p.isOnline()) {
					return;
				}
				Location serverSpawn = SimpleBases.getSimpleBasesDataManager().getServerSpawn();
				p.teleport(serverSpawn);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("spawnSuccess"));
			}, setting.getInt("countDownTime"));
		}, (sender, args) -> {
			return null;
		}, true, new CommandArgument.Builder("死亡传送至此").ignoreArg("false").build(),
				new CommandArgument.Builder("玩家加入服务器传送至此").ignoreArg("false").build());
	}

	// init spawnListener
	static {
		Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void playerJoinServer(PlayerJoinEvent e) {
				if (SimpleBases.getSimpleBasesDataManager().getServerSpawnJoinTpSpawn()) {
					e.getPlayer().teleport(SimpleBases.getSimpleBasesDataManager().getServerSpawn());
				}
			}

			@EventHandler
			public void playerRespawnEvent(PlayerRespawnEvent e) {
				if (SimpleBases.getSimpleBasesDataManager().getServerSpawnDeathTpSpawn()) {
					e.setRespawnLocation(SimpleBases.getSimpleBasesDataManager().getServerSpawn());
				}
			}
		}, SimpleBases.getInstance());
	}

	private void setSpawnCommand() {
		registerMainCommand("setspawn", "设置出生点", (sender, args) -> {
			Player p = (Player) sender;

			boolean deathTpSpawn = false;
			boolean joinTpSpawn = false;
			if (args.length > 1) {
				deathTpSpawn = Boolean.parseBoolean(args[0]);
				joinTpSpawn = Boolean.parseBoolean(args[1]);
			}
			Location serverSpawn = p.getLocation();
			SimpleBases.getSimpleBasesDataManager().setServerSpawn(serverSpawn, deathTpSpawn, joinTpSpawn);
			p.sendMessage(SimpleBases.getMessageManager().getMessage("setSpawnSuccess",
					"world:" + serverSpawn.getWorld().getName(), "x:" + Decimal.decimalFormat(serverSpawn.getX()),
					"y:" + Decimal.decimalFormat(serverSpawn.getY()),
					"z:" + Decimal.decimalFormat(serverSpawn.getZ())));
		}, (sender, args) -> {
			if (args.length == 1 || args.length == 2)
				return Arrays.asList("true", "false");
			return null;
		}, true, new CommandArgument.Builder("死亡传送至此").ignoreArg("false").build(),
				new CommandArgument.Builder("玩家加入服务器传送至此").ignoreArg("false").build());

	}

	private void hatCommand() {
		registerMainCommand("hat", "将手中物品戴到脑壳上", (sender, args) -> {
			Player p = (Player) sender;
			ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
			if (itemInMainHand == null || itemInMainHand.getType() == Material.AIR) {
				p.sendMessage(SimpleBases.getMessageManager().getMessage("itemInMainHandIsNull"));
				return;
			}
			if (instance.getSetting("hat").getStringList("disItem").contains(itemInMainHand.getType().toString())) {
				p.sendMessage(SimpleBases.getMessageManager().getMessage("itemIsDisable"));
				return;
			}
			ItemStack helmet = p.getInventory().getHelmet();
			p.getInventory().setHelmet(itemInMainHand);
			p.getInventory().setItemInMainHand(helmet);
			p.sendMessage(SimpleBases.getMessageManager().getMessage("youHatSuccess",
					"itemName:" + itemInMainHand.getItemMeta().getDisplayName()));
			CraftServer s = (CraftServer) Bukkit.getServer();

			p.sendMessage(s.getItemFactory().getItemMeta(itemInMainHand.getType()).getDisplayName());
		}, (sender, args) -> {
			return null;
		}, true);
	}

	private void suicideCommand() {
		registerMainCommand("suicide", "自杀", (sender, args) -> {
			Player p = (Player) sender;
			p.setHealth(0.0);
			p.remove();
			p.sendMessage(SimpleBases.getMessageManager().getMessage("youSuicideSuccess"));
		}, (sender, args) -> {
			return null;
		}, true);
	}

	private void gmCommand() {
		registerMainCommand("gm", "改变游戏模式", (sender, args) -> {
			Player p = (Player) sender;
			Player setPlayer = p;
			if (args.length > 1) {
				if ((setPlayer = getSetPlayer(args[1], p)) == null)
					return;
			}
			if ("0".equals(args[0]) || "survival".equals(args[0])) {
				setPlayer.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("setGamemodeToSurvival",
						"player:" + setPlayer.getName()));
				return;
			}
			if ("1".equals(args[0]) || "creative".equals(args[0])) {
				setPlayer.setGameMode(GameMode.CREATIVE);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("setGamemodeToCreative",
						"player:" + setPlayer.getName()));
				return;
			}
			if ("2".equals(args[0]) || "adventure".equals(args[0])) {
				setPlayer.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("setGamemodeToAdventure",
						"player:" + setPlayer.getName()));
				return;
			}
			if ("3".equals(args[0]) || "spectator".equals(args[0])) {
				setPlayer.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(SimpleBases.getMessageManager().getMessage("setGamemodeToSpectator",
						"player:" + setPlayer.getName()));
				return;
			}
			p.sendMessage(SimpleBases.getMessageManager().getMessage("notFindGamemode"));

		}, (sender, args) -> {
			if (args.length == 1)
				return Arrays.asList("0", "1", "2", "3", "survival", "creative", "adventure", "spectator");
			if (args.length == 2)
				return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
			return null;
		}, true, new CommandArgument.Builder("0或survival/1或creative/2或adventure/3或spectator").build(),
				new CommandArgument.Builder("玩家").ignoreArg("").build());
	}

	private void speedCommand() {
		registerMainCommand("speed", "改变 移动/飞行 速度", (sender, args) -> {
			Player p = (Player) sender;
			float speed = Float.valueOf(args[1]);
			if (speed <= 1 && speed >= -1) {
				Player setPlayer = p;
				if (args.length > 2) {
					if ((setPlayer = getSetPlayer(args[2], p)) == null)
						return;
				}
				if ("walk".equals(args[0])) {
					setPlayer.setWalkSpeed(speed);
					p.sendMessage(SimpleBases.getMessageManager().getMessage("setWalkSuccess",
							"player:" + setPlayer.getName(), "speed:" + speed));
				}
				if ("fly".equals(args[0])) {
					setPlayer.setFlySpeed(speed);
					p.sendMessage(SimpleBases.getMessageManager().getMessage("setFlySuccess",
							"player:" + setPlayer.getName(), "speed:" + speed));
				}
				return;
			}
			p.sendMessage(SimpleBases.getMessageManager().getMessage("speedWrong"));
		}, (sender, args) -> {
			if (args.length == 1)
				return Arrays.asList("walk", "fly");
			if (args.length == 2)
				return Arrays.asList("0.2");
			if (args.length == 3)
				return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
			return null;
		}, true, new CommandArgument.Builder("walk/fly").build(), new CommandArgument.Builder("1~-1").build(),
				new CommandArgument.Builder("玩家").ignoreArg("").build());
	}

	private void flyCommand() {
		registerMainCommand("fly", "改变飞行模式", (sender, args) -> {
			Player p = (Player) sender;
			Player setPlayer = p;
			if (args.length > 0) {
				if ((setPlayer = getSetPlayer(args[0], p)) == null)
					return;
			}
			if (p.getAllowFlight()) {
				setPlayer.setAllowFlight(false);
				p.sendMessage(
						SimpleBases.getMessageManager().getMessage("switchToOffFly", "player:" + setPlayer.getName()));
				return;
			}
			setPlayer.setAllowFlight(true);
			p.sendMessage(SimpleBases.getMessageManager().getMessage("switchToOnFly", "player:" + setPlayer.getName()));
		}, (sender, args) -> {
			if (args.length == 1)
				return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
			return null;
		}, true, new CommandArgument.Builder("玩家").ignoreArg("").build());
	}

	// XXX common
	private Player getSetPlayer(String playerName, Player p) {
		Player setPlayer = p;
		if (!"".equals(playerName)) {
			setPlayer = Bukkit.getPlayerExact(playerName);
			if (setPlayer == null) {
				p.sendMessage(SimpleBases.getMessageManager().getMessage("playerOfflineOrNoExistent",
						"player:" + playerName));
			}
		}
		return setPlayer;
	}

	@SuppressWarnings("all")
	private void gcCommand() {
		registerMainCommand("gc", "查询服务器详细信息", (sender, args) -> {
			int byteToMb = 1024 * 1024;
			Runtime rt = Runtime.getRuntime();
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
			sender.sendMessage("§9§l======================================");
			sender.sendMessage("§a操作系统: §e" + os + " " + version + "(" + arch + ")");
			sender.sendMessage("§a操作系统总物理内存: §e" + physicalTotal + " §aMB");
			sender.sendMessage("§a操作系统已用物理内存: §e" + physicalUse + " §aMB");
			sender.sendMessage("§a操作系统空闲物理内存: §e" + physicalFree + " §aMB");
			sender.sendMessage("§9§l======================================");
			String t = "§e";
			for (String st : list) {
				t = t + " " + st;
			}
			sender.sendMessage("§a服务器启动参数: §e" + t);
			sender.sendMessage("§9§l======================================");
			sender.sendMessage("§a运行时间: §e" + Utils
					.formatDate(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()));
			try {
				Class<?> nmsClass = NmsUtil.getNmsClass("MinecraftServer");

				Field recentTps = nmsClass.getDeclaredField("recentTps");
				recentTps.setAccessible(true);

				StringBuilder sb = new StringBuilder("§6当前TPS: 1m, 5m, 15m: ");
				for (double tps : (double[]) recentTps
						.get(nmsClass.getDeclaredMethod("getServer", null).invoke(null, null))) {
					sb.append(format(tps));
					sb.append(", ");
				}
				sender.sendMessage(sb.substring(0, sb.length() - 2));
			} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
					| IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			sender.sendMessage("§a当前内存使用情况: §e"
					+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024))
					+ "§a/§e" + (Runtime.getRuntime().totalMemory() / (1024 * 1024)) + " §aMB (Max: §e"
					+ (Runtime.getRuntime().maxMemory() / (1024 * 1024)) + " §aMB)");
			sender.sendMessage("§aJvm使用处理器个数: §e" + Cpunumber);
			sender.sendMessage("§a线程: §e" + dumpAllThreads.length);
			sender.sendMessage("§9§l======================================");
			for (World wd : Bukkit.getWorlds()) {
				int tileEntities = 0;
				try {
					for (Chunk chunk : wd.getLoadedChunks()) {
						tileEntities += chunk.getTileEntities().length;
					}
				} catch (ClassCastException ex) {
					Bukkit.getLogger().log(Level.SEVERE, "无法读取 错误的世界" + wd, ex);
				}
				sender.sendMessage("§a世界: §e" + wd.getName() + "§a - §e" + wd.getEnvironment().toString()
						+ " §a区块数量: §e" + wd.getLoadedChunks().length + " §a实体数量: §e"
						+ Integer.valueOf(wd.getEntities().size()) + " §aTiles: §e" + Integer.valueOf(tileEntities)
						+ " §a玩家数量: §e" + Integer.valueOf(wd.getPlayers().size()));
				HashMap<String, Integer> worldentity = new HashMap<>();
				wd.getEntities().forEach((s) -> {
					if (!worldentity.containsKey("§e" + s.getType().toString() + "§a")) {
						worldentity.put("§e" + s.getType().toString() + "§a", 1);
					} else {
						worldentity.put("§e" + s.getType().toString() + "§a",
								worldentity.get("§e" + s.getType().toString() + "§a") + 1);
					}
				});
				List<String> players = new ArrayList<>();
				wd.getPlayers().forEach((s) -> {
					players.add(s.getName());
				});
				sender.sendMessage("§a详细实体: " + worldentity.toString().replaceAll("=", " =§e ").replaceAll(", ", "§a, ")
						.replaceAll("}", "§a}"));
				sender.sendMessage("§a详细玩家: " + players.toString().replaceAll("\\[", "\\[§e").replaceAll(", ", "§a, §e")
						.replaceAll("\\]", "§a\\]"));
			}
			sender.sendMessage("§9§l=============================================");
			File[] files = File.listRoots();
			for (File file : files) {
				String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024) + "§aG";
				String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "§aG";
				String use = new DecimalFormat("#.#")
						.format((file.getTotalSpace() - file.getFreeSpace()) * 1.0 / 1024 / 1024 / 1024) + "G";
				String path = file.getPath();
				sender.sendMessage("§e" + path + "§a总大小: §e" + total + "§a;已用空间: §e" + use + "§a;剩余空间: §e" + free);
			}
			sender.sendMessage("§9§l=============================================");
		}, (sender, args) -> {
			return null;
		}, false);

	}

	private String format(double tps) {
		return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED).toString()
				+ ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
	}

	// 注册指令的一些方法
	private boolean isEnable(String cmd) {
		return instance.getConfig().getBoolean(StringUtil.joining(".", "CommandEnable", cmd));
	}

	private CommandPlus.Builder createCommandPlus(String cmd) {
		return new CommandPlus.Builder(cmd, instance);
	}

	private Command.Builder createMainCommand(String cmd, String describe,
			ExecuteComponentFunctional executeComponentFunctional, TabCompleterFunctional tabCompleterFunctional) {
		return new Command.Builder(cmd, describe).executeComponent(executeComponentFunctional)
				.tabCompleter(tabCompleterFunctional);
	}

	private void registerMainCommand(String cmd, String describe, ExecuteComponentFunctional executeComponentFunctional,
			TabCompleterFunctional tabCompleterFunctional, boolean onlyPlayerUse, CommandArgument... args) {
		if (!isEnable(cmd))
			return;
		Command mainCommand = createMainCommand(cmd, describe, executeComponentFunctional, tabCompleterFunctional)
				.args(args).onlyPlayerUse(onlyPlayerUse).build();

		registerCommand.put(cmd, mainCommand);
		createCommandPlus(cmd).mainCommand(mainCommand).build().registerThis();
		mainCommand.setHideNoPermissionHelp(true);
	}

}
