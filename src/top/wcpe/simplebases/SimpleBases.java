package top.wcpe.simplebases;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import top.wcpe.simplebases.manager.SimpleBasesFileDataManagerImpl;
import top.wcpe.simplebases.manager.SimpleBasesDataManager;
import top.wcpe.wcpelib.bukkit.WcpeLib;
import top.wcpe.wcpelib.bukkit.manager.MessageManager;
import top.wcpe.wcpelib.common.utils.string.StringUtil;

/**
 * 插件主类
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:12:38
 */
public class SimpleBases extends JavaPlugin {
	public final static double VERSION = 2.0;

	public void log(String log) {
		getServer().getConsoleSender().sendMessage("§a[§e" + this.getName() + "§a]§r" + log);
	}

	@Getter
	private static SimpleBases instance;
	@Getter
	private static SimpleBasesDataManager simpleBasesDataManager;
	@Getter
	private static MessageManager messageManager;

	public ConfigurationSection getSetting(String key) {
		return getConfig().getConfigurationSection(StringUtil.joining(".", "Setting", key));
	}

	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();
		getServer().getOnlinePlayers().forEach(Player::closeInventory);
		instance = this;
		if (WcpeLib.isEnableMysql())
			simpleBasesDataManager = null;
		else
			simpleBasesDataManager = new SimpleBasesFileDataManagerImpl(this);
		messageManager = new MessageManager(this, "CN");
		new SimpleBasesCommand(this).buildCommand();
		new SimpleBasesBasicFunctions(this);
		log("§1   _________.__               .__        __________                              ");
		log("§2  /   _____/|__| _____ ______ |  |   ____\\______   \\_____    ______ ____   ______");
		log("§3  \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\|    |  _/\\__  \\  /  ___// __ \\ /  ___/");
		log("§4  /        \\|  |  Y Y  \\  |_> >  |_\\  ___/|    |   \\ / __ \\_\\___ \\\\  ___/ \\___ \\ ");
		log("§5 /_______  /|__|__|_|  /   __/|____/\\___  >______  /(____  /____  >\\___  >____  >");
		log("§6         \\/          \\/|__|             \\/       \\/      \\/     \\/     \\/     \\/");
		log("§7                             §aVersion: 2.0                                                              ");
		log("§a 插件加载完成 共耗时:§e" + (System.currentTimeMillis() - start) + "§aMs");
	}

	@Override
	public void onDisable() {
		getServer().getOnlinePlayers().forEach(Player::closeInventory);
		log("§1   _________.__               .__        __________                              ");
		log("§2  /   _____/|__| _____ ______ |  |   ____\\______   \\_____    ______ ____   ______");
		log("§3  \\_____  \\ |  |/     \\\\____ \\|  | _/ __ \\|    |  _/\\__  \\  /  ___// __ \\ /  ___/");
		log("§4  /        \\|  |  Y Y  \\  |_> >  |_\\  ___/|    |   \\ / __ \\_\\___ \\\\  ___/ \\___ \\ ");
		log("§5 /_______  /|__|__|_|  /   __/|____/\\___  >______  /(____  /____  >\\___  >____  >");
		log("§6         \\/          \\/|__|             \\/       \\/      \\/     \\/     \\/     \\/");
		log("§7                             §aVersion: 2.0                                                              ");
		log("§c SimpleBases 插件已卸载完毕！");
	}

}
