package top.wcpe.simplebases.playerdata;

import java.util.List;

import org.bukkit.Location;

import top.wcpe.simplebases.playerdata.filedata.PlayerHome.Home;
import top.wcpe.simplebases.playerdata.filedata.PlayerOfflineLocation;
import top.wcpe.simplebases.playerdata.filedata.PlayerOnlineLocation;

/**
 * 玩家数据操纵接口
 * 
 * @author WCPE
 * @date 2021年5月12日 下午5:23:46
 */
public interface PlayerData {

	String getName();

	void putHome(String playerName, Home playerHome);

	void saveHome(String playerName);

	boolean delHome(String playerName, String homeName);

	PlayerOfflineLocation getPlayerOfflineLocation(long time);

	List<PlayerOfflineLocation> listPlayerOfflineLocation();

	void savePlayerOfflineLocation(long time, Location location);

	List<PlayerOnlineLocation> listPlayerOnlineLocation();

	PlayerOnlineLocation getPlayerOnlineLocation(long time);

	void savePlayerOnlineLocation(long time, Location location);
}
