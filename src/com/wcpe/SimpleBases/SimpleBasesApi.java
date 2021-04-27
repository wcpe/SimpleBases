package com.wcpe.SimpleBases;

public class SimpleBasesApi {
	/**
	 * @param name 玩家名称
	 * 是否开启屏蔽
	 */
	public static Boolean isEnablePb(String name) {
		Boolean boolean1 = Main.getInstance().getData().getPb().get(name);
		if (boolean1 == null) {
			return false;
		}
		return boolean1;
	}
}
