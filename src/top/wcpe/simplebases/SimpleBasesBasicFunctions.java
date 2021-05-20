package top.wcpe.simplebases;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import top.wcpe.simplebases.playerdata.PlayerData;
import top.wcpe.wcpelib.bukkit.utils.NetMinecraftServerUtil;
import top.wcpe.wcpelib.bukkit.utils.StringActionUtil;
import top.wcpe.wcpelib.common.utils.string.StringUtil;

/**
 * 插件基础功能实现类
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:12:29
 */
public class SimpleBasesBasicFunctions implements Listener {

	private final SimpleBases instance;

	public SimpleBasesBasicFunctions(SimpleBases instance) {
		this.instance = instance;
		instance.getServer().getPluginManager().registerEvents(this, instance);
	}

	/**
	 * 玩家首次加入游戏功能
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月20日 下午4:27:00
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void playerfirstJoin(PlayerJoinEvent e) {
		if (SimpleBases.getSimpleBasesDataManager().existPlayerData(e.getPlayer().getName())) {
			return;
		}
		SimpleBases.getSimpleBasesDataManager().createPlayerData(e.getPlayer().getName());
		ConfigurationSection setting = instance.getSetting("playerFirstJoin");
		if (setting.getBoolean("enable")) {
			StringActionUtil.executionCommands(setting.getStringList("stringAction"), false, e.getPlayer());
		}
	}

	/**
	 * 玩家加入游戏位置记录
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:01:37
	 */
	@EventHandler
	public void joinServerRecord(PlayerJoinEvent e) {
		PlayerData pd = SimpleBases.getSimpleBasesDataManager().getPlayerData(e.getPlayer().getName());
		if (pd != null)
			pd.savePlayerOnlineLocation(System.currentTimeMillis(), e.getPlayer().getLocation());

	}

	/**
	 * 玩家离开游戏位置记录
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:01:55
	 */
	@EventHandler
	public void quitServerRecord(PlayerQuitEvent e) {
		PlayerData pd = SimpleBases.getSimpleBasesDataManager().getPlayerData(e.getPlayer().getName());
		if (pd != null)
			pd.savePlayerOfflineLocation(System.currentTimeMillis(), e.getPlayer().getLocation());
	}

	/**
	 * 更新传送上一次位置
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:02:05
	 */
	@EventHandler
	public void backRecord(PlayerTeleportEvent e) {
		PlayerBufferArea.getPlayerBackLocation().put(e.getPlayer().getName(), e.getFrom());
	}

	/**
	 * 玩家加入的全服消息
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:02:18
	 */
	@EventHandler
	public void playerJoinMessage(PlayerJoinEvent e) {
		ConfigurationSection setting = instance.getSetting("joinQuitMessage");

		if (setting.getBoolean(StringUtil.joining(".", "joinMessage", "enable"))) {
			String defaultMessage = setting.getString(StringUtil.joining(".", "joinMessage", "default"));
			ConfigurationSection permissionMessage = setting
					.getConfigurationSection(StringUtil.joining(".", "joinMessage", "permissionMessage"));
			for (String s : permissionMessage.getKeys(false)) {
				if (e.getPlayer().hasPermission(s)) {
					defaultMessage = permissionMessage.getString(s);
				}
			}
			e.setJoinMessage(StringUtil.replaceString(defaultMessage, "player:" + e.getPlayer().getName()));
		}
	}

	/**
	 * 玩家离开的全服消息
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:02:30
	 */
	@EventHandler
	public void playerquitMessage(PlayerQuitEvent e) {
		ConfigurationSection setting = instance.getSetting("joinQuitMessage");

		if (setting.getBoolean(StringUtil.joining(".", "quitMessage", "enable"))) {

			String defaultMessage = setting.getString(StringUtil.joining(".", "quitMessage", "default"));
			ConfigurationSection permissionMessage = setting
					.getConfigurationSection(StringUtil.joining(".", "quitMessage", "permissionMessage"));
			for (String s : permissionMessage.getKeys(false)) {
				if (e.getPlayer().hasPermission(s)) {
					defaultMessage = permissionMessage.getString(s);
				}
			}
			e.setQuitMessage(StringUtil.replaceString(defaultMessage, "player:" + e.getPlayer().getName()));
		}
	}

	/**
	 * 玩家进入服务器的提示
	 * 
	 * @param e
	 * @author WCPE
	 * @date 2021年5月8日 下午3:03:33
	 */
	@EventHandler
	public void joinServerSendPlayerMessage(PlayerJoinEvent e) {
		ConfigurationSection setting = instance.getSetting("joinServerSendPlayerMessage");

		if (setting.getBoolean(StringUtil.joining(".", "sendMessage", "enable"))) {
			for (String message : setting.getStringList(StringUtil.joining(".", "sendMessage", "message"))) {
				e.getPlayer().sendMessage(StringUtil.replaceString(message, "player:" + e.getPlayer().getName()));
			}
		}
		if (setting.getBoolean(StringUtil.joining(".", "sendActionbar", "enable"))) {
			NetMinecraftServerUtil.sendAction(e.getPlayer(),
					StringUtil.replaceString(setting.getString(StringUtil.joining(".", "sendActionbar", "message")),
							"player:" + e.getPlayer().getName()));
		}
		if (setting.getBoolean(StringUtil.joining(".", "sendTitle", "enable"))) {
			e.getPlayer().sendTitle(
					StringUtil.replaceString(setting.getString(StringUtil.joining(".", "sendTitle", "mainMessage")),
							"player:" + e.getPlayer().getName()),
					StringUtil.replaceString(setting.getString(StringUtil.joining(".", "sendTitle", "subMessage")),
							"player:" + e.getPlayer().getName()),
					setting.getInt(StringUtil.joining(".", "sendTitle", "fadein")),
					setting.getInt(StringUtil.joining(".", "sendTitle", "stay")),
					setting.getInt(StringUtil.joining(".", "sendTitle", "fadeout")));
		}

	}

}
