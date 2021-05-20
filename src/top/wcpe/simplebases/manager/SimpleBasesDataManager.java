package top.wcpe.simplebases.manager;

import java.util.List;

import org.bukkit.Location;

import top.wcpe.simplebases.playerdata.PlayerData;

/**
 * 数据管理接口
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:11:23
 */
public interface SimpleBasesDataManager {
	void setServerSpawn(Location location, boolean deathTpSpawn, boolean joinTpSpawn);

	Location getServerSpawn();

	boolean getServerSpawnDeathTpSpawn();

	boolean getServerSpawnJoinTpSpawn();

	boolean existPlayerData(String playerName);

	PlayerData getPlayerData(String playerName);

	List<String> listPlayerName();

	PlayerData createPlayerData(String playerName);

	boolean deletePlayerData(String playerName);

}
