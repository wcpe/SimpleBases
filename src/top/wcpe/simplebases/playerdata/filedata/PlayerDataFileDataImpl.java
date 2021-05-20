package top.wcpe.simplebases.playerdata.filedata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import top.wcpe.simplebases.playerdata.PlayerData;
import top.wcpe.simplebases.playerdata.filedata.PlayerHome.Home;

/**
 * 玩家操纵数据本地文件实现
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:11:59
 */
public class PlayerDataFileDataImpl implements PlayerData {
	@Getter
	private final String name;
	private final Path playerDataDir;

	public PlayerDataFileDataImpl(String name, Path playerDataDir) {
		this.name = name;
		this.playerDataDir = playerDataDir;
	}

	@Getter
	private final HashMap<String, PlayerHome> homes = new HashMap<>();

	@Override
	public void putHome(String playerName, Home playerHome) {
		PlayerHome ph = this.homes.get(playerName);
		if (ph == null) {
			ph = new PlayerHome(playerName);
			this.homes.put(playerName, ph);
		}
		ph.getHome().add(playerHome);
	}

	@Override
	public void saveHome(String playerName) {
		PlayerHome ph = this.getHomes().get(playerName);
		if (ph == null) {
			return;
		}
		Path homesPath = playerDataDir.resolve("homes");
		if (!Files.exists(homesPath)) {
			try {
				Files.createDirectory(homesPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (Home home : ph.getHome()) {
			Path path = homesPath.resolve(home.getName() + ".yml");
			YamlConfiguration yaml = new YamlConfiguration();
			yaml.set("name", home.getName());
			yaml.set("createTime", home.getCreateTime());
			yaml.set("location", home.getLocation());
			try {
				yaml.save(path.toFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean delHome(String playerName, String homeName) {
		PlayerHome ph = this.getHomes().get(playerName);
		if (ph == null) {
			return false;
		}
		for (Home home : ph.getHome()) {
			if (home.getName().equals(homeName)) {
				ph.getHome().remove(home);
				return true;
			}
		}
		return false;
	}

	private final HashMap<Long, PlayerOfflineLocation> playerOfflineLocation = new HashMap<>();

	@Override
	public PlayerOfflineLocation getPlayerOfflineLocation(long time) {
		PlayerOfflineLocation playerOfflineLocation = this.playerOfflineLocation.get(time);
		if (playerOfflineLocation != null) {
			return playerOfflineLocation;
		}
		Path offlineLocationsPath = playerDataDir.resolve("offlineLocations");
		if (Files.exists(offlineLocationsPath)) {
			Path timePath = offlineLocationsPath.resolve(time + ".yml");
			if (Files.exists(timePath)) {
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(timePath.toFile());
				String worldName = yaml.getString("world");
				if (worldName != null) {
					World world = Bukkit.getWorld(worldName);
					if (world != null) {
						PlayerOfflineLocation pfl = new PlayerOfflineLocation(time,
								new Location(world, yaml.getDouble("x"), yaml.getDouble("y"), yaml.getDouble("z")));
						this.playerOfflineLocation.put(time, pfl);
						return pfl;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void savePlayerOfflineLocation(long time, Location location) {
		this.playerOfflineLocation.put(time, new PlayerOfflineLocation(time, location));
		Path offlineLocationsPath = playerDataDir.resolve("offlineLocations");
		if (!Files.exists(offlineLocationsPath)) {
			try {
				Files.createDirectory(offlineLocationsPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Path timePath = offlineLocationsPath.resolve(time + ".yml");
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("world", location.getWorld().getName());
		yaml.set("x", location.getX());
		yaml.set("y", location.getY());
		yaml.set("z", location.getZ());
		try {
			yaml.save(timePath.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PlayerOfflineLocation> listPlayerOfflineLocation() {
		Path offlineLocationsPath = playerDataDir.resolve("offlineLocations");
		if (!Files.exists(offlineLocationsPath)) {
			return new ArrayList<>();
		}
		List<PlayerOfflineLocation> list = new ArrayList<>();
		for (File file : offlineLocationsPath.toFile().listFiles()) {
			String fileName = file.getName();
			int lastIndexOf = fileName.lastIndexOf(".");
			if (lastIndexOf != -1) {
				try {
					list.add(getPlayerOfflineLocation(Long.parseLong(fileName.substring(0, lastIndexOf))));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private final HashMap<Long, PlayerOnlineLocation> playerOnlineLocation = new HashMap<>();

	@Override
	public PlayerOnlineLocation getPlayerOnlineLocation(long time) {
		PlayerOnlineLocation playerOnlineLocation = this.playerOnlineLocation.get(time);
		if (playerOnlineLocation != null) {
			return playerOnlineLocation;
		}
		Path offlineLocationsPath = playerDataDir.resolve("onlineLocations");
		if (Files.exists(offlineLocationsPath)) {
			Path timePath = offlineLocationsPath.resolve(time + ".yml");
			if (Files.exists(timePath)) {
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(timePath.toFile());
				String worldName = yaml.getString("world");
				if (worldName != null) {
					World world = Bukkit.getWorld(worldName);
					if (world != null) {
						PlayerOnlineLocation pol = new PlayerOnlineLocation(time,
								new Location(world, yaml.getDouble("x"), yaml.getDouble("y"), yaml.getDouble("z")));
						this.playerOnlineLocation.put(time, pol);
						return pol;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void savePlayerOnlineLocation(long time, Location location) {
		this.playerOnlineLocation.put(time, new PlayerOnlineLocation(time, location));
		Path onlineLocationsPath = playerDataDir.resolve("onlineLocations");
		if (!Files.exists(onlineLocationsPath)) {
			try {
				Files.createDirectory(onlineLocationsPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Path timePath = onlineLocationsPath.resolve(time + ".yml");
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("world", location.getWorld().getName());
		yaml.set("x", location.getX());
		yaml.set("y", location.getY());
		yaml.set("z", location.getZ());
		try {
			yaml.save(timePath.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PlayerOnlineLocation> listPlayerOnlineLocation() {
		Path onlineLocationsPath = playerDataDir.resolve("onlineLocations");
		if (!Files.exists(onlineLocationsPath)) {
			return new ArrayList<>();
		}

		List<PlayerOnlineLocation> list = new ArrayList<>();
		for (File file : onlineLocationsPath.toFile().listFiles()) {
			String fileName = file.getName();
			int lastIndexOf = fileName.lastIndexOf(".");
			if (lastIndexOf != -1) {
				try {
					list.add(getPlayerOnlineLocation(Long.parseLong(fileName.substring(0, lastIndexOf))));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
