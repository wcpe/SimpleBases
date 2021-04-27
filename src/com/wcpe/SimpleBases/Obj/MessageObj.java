package com.wcpe.SimpleBases.Obj;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import com.wcpe.SimpleBases.Main;

public class MessageObj {
	private String lang;
	public MessageObj(String lang) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		this.lang = lang;
		HashMap<String, String> l = new HashMap<String, String>();
		for(String path:getFile().getConfigurationSection("Message").getKeys(false)) {
			 l.put(path, getFile().getString("Message."+path).replaceAll("&", "§"));
		}
		Field field = getClass().getDeclaredField(lang);
        field.setAccessible(true);
        field.set(this, l);
		
	}
	public static YamlConfiguration getFile() {
		File file = new File(Main.getInstance().getDataFolder(),"Message.yml");
		YamlConfiguration a = YamlConfiguration.loadConfiguration(file);
		return a;
	}
	private HashMap<String, String> cn = new HashMap<String, String>();

	/**
	 * @return cn
	 */
	public final HashMap<String, String> getMessage() {
		return cn;
	}

	/**
	 * @param cn 要设置的 cn
	 */
	public final void setMessage(HashMap<String, String> cn) {
		this.cn = cn;
	}
	/**
	 * @return lang
	 */
	public final String getLang() {
		return lang;
	}
	
}
