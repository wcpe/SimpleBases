package top.wcpe.simplebases.playerdata.filedata;

import org.bukkit.Location;

import lombok.Data;

/**
 * 玩家上线位置
 * 
 * @author WCPE
 * @date 2021年5月12日 下午9:02:21
 */
@Data
public class PlayerOnlineLocation {
	private long timestamp;
	private Location location;

	public PlayerOnlineLocation(long timestamp, Location location) {
		this.timestamp = timestamp;
		this.location = location;
	}

}
