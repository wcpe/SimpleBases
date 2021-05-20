package top.wcpe.simplebases;

import java.util.HashMap;

import org.bukkit.Location;

import lombok.Getter;
/**
 * 插件临时缓存区域
 * @author WCPE
 * @date 2021年5月8日 下午2:12:44
 */
public class PlayerBufferArea {
	@Getter
	public static HashMap<String, Location> playerBackLocation = new HashMap<>();
}
