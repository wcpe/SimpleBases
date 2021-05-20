package top.wcpe.simplebases.playerdata.filedata;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import lombok.Data;

/**
 * 玩家的 家 本地实现
 * 
 * @author WCPE
 * @date 2021年5月8日 下午2:11:48
 */
@Data
public class PlayerHome {
	private String playerName;
	private List<Home> home = new ArrayList<>();

	public PlayerHome(String playerName) {
		this.playerName = playerName;
	}

	@Data
	public static class Home {
		private String name;
		private long createTime;
		private Location location;
	}
}
