package top.wcpe.simplebases.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import top.wcpe.simplebases.SimpleBases;
import top.wcpe.simplebases.playerdata.PlayerData;
import top.wcpe.simplebases.playerdata.filedata.PlayerDataFileDataImpl;

/**
 * 玩家数据管理 本地文件存储实现
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:11:05
 */
public class SimpleBasesFileDataManagerImpl implements SimpleBasesDataManager {
	private final Path playerDataDirPath;
	private final Path serverDataDirPath;

	public SimpleBasesFileDataManagerImpl(SimpleBases instance) {
		Path path = instance.getDataFolder().toPath();
		Path playerdataDir = path.resolve("playerdata");
		if (!Files.exists(playerdataDir)) {
			try {
				Files.createDirectories(playerdataDir);
			} catch (IOException e) {
				e.printStackTrace();
				instance.getLogger().info("create playerdata Director fail!");
			}
		}
		this.playerDataDirPath = playerdataDir;
		Path serverdataDir = path.resolve("serverdata");
		if (!Files.exists(serverdataDir)) {
			try {
				Files.createDirectories(serverdataDir);
			} catch (IOException e) {
				e.printStackTrace();
				instance.getLogger().info("create serverdata Director fail!");
			}
		}
		this.serverDataDirPath = serverdataDir;
	}

	@Override
	public void setServerSpawn(Location location, boolean deathTpSpawn, boolean joinTpSpawn) {
		this.serverSpawnLocation = location;
		this.deathTpSpawn = deathTpSpawn;
		this.joinTpSpawn = joinTpSpawn;
		Path serverSpawn = serverDataDirPath.resolve("serverSpawn.yml");
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("world", location.getWorld().getName());
		yaml.set("x", location.getX());
		yaml.set("y", location.getY());
		yaml.set("z", location.getZ());
		yaml.set("deathTpSpawn", deathTpSpawn);
		yaml.set("joinTpSpawn", joinTpSpawn);
		try {
			yaml.save(serverSpawn.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Location serverSpawnLocation;
	private Boolean deathTpSpawn;
	private Boolean joinTpSpawn;

	@Override
	public Location getServerSpawn() {
		if (serverSpawnLocation != null) {
			return serverSpawnLocation;
		}
		Path serverSpawn = serverDataDirPath.resolve("serverSpawn.yml");
		if (Files.exists(serverSpawn)) {
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(serverSpawn.toFile());
			String worldName = yaml.getString("world");
			if (worldName != null) {
				World world = Bukkit.getWorld(worldName);
				if (world != null) {
					serverSpawnLocation = new Location(world, yaml.getDouble("x"), yaml.getDouble("y"),
							yaml.getDouble("z"));
					return serverSpawnLocation;
				}
			}
		}
		serverSpawnLocation = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
		return serverSpawnLocation;
	}

	@Override
	public boolean getServerSpawnDeathTpSpawn() {
		if (this.deathTpSpawn != null) {
			return this.deathTpSpawn;
		}
		Path serverSpawn = serverDataDirPath.resolve("serverSpawn.yml");
		if (Files.exists(serverSpawn)) {
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(serverSpawn.toFile());
			return yaml.getBoolean("deathTpSpawn", false);
		}
		this.deathTpSpawn = false;
		return false;
	}

	@Override
	public boolean getServerSpawnJoinTpSpawn() {
		if (this.joinTpSpawn != null) {
			return this.joinTpSpawn;
		}
		Path serverSpawn = serverDataDirPath.resolve("serverSpawn.yml");
		if (Files.exists(serverSpawn)) {
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(serverSpawn.toFile());
			return yaml.getBoolean("joinTpSpawn", false);
		}
		this.joinTpSpawn = false;
		return false;
	}

	private final HashMap<String, PlayerData> playerDataMap = new HashMap<>();

	@Override
	public boolean existPlayerData(String playerName) {
		if (playerDataMap.containsKey(playerName)) {
			return true;
		}
		return Files.exists(this.playerDataDirPath.resolve(playerName));
	}

	@Override
	public PlayerData getPlayerData(String playerName) {
		if (!existPlayerData(playerName)) {
			return null;
		}
		PlayerData playerData = this.playerDataMap.get(playerName);
		if (playerData != null) {
			return playerData;
		}
		Path playerDataDir = this.playerDataDirPath.resolve(playerName);
		if (!Files.exists(playerDataDir)) {
			return null;
		}
		PlayerDataFileDataImpl playerDataFileDataImpl = new PlayerDataFileDataImpl(playerName, playerDataDir);
		this.playerDataMap.put(playerName, playerDataFileDataImpl);
		return playerDataFileDataImpl;
	}

	@Override
	public List<String> listPlayerName() {
		List<String> list = new ArrayList<>();
		for (File file : playerDataDirPath.toFile().listFiles()) {
			list.add(file.getName());
		}
		return list;
	}

	@Override
	public PlayerData createPlayerData(String playerName) {
		Path playerDataDir = playerDataDirPath.resolve(playerName);
		if (!Files.exists(playerDataDir)) {
			try {
				Files.createDirectory(playerDataDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PlayerDataFileDataImpl playerDataFileDataImpl = new PlayerDataFileDataImpl(playerName, playerDataDir);
		this.playerDataMap.put(playerName, playerDataFileDataImpl);
		return playerDataFileDataImpl;
	}

	@Override
	public boolean deletePlayerData(String playerName) {
		this.playerDataMap.remove(playerName);
		Path playerDataDir = playerDataDirPath.resolve(playerName);
		if (Files.exists(playerDataDir)) {
			try {
				Files.delete(playerDataDir);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
