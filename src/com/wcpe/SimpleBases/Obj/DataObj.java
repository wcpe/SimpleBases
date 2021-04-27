package com.wcpe.SimpleBases.Obj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class DataObj implements ConfigurationSerializable {
	public DataObj() {
		Back = new HashMap<String, Location>();
		Home = new HashMap<String, HashMap<String, Location>>();
		Spawn = null;
		Warp = new HashMap<String, Location>();
		PlayerOnline = new HashMap<String, HashMap<String, Location>>();
		PlayerOffline = new HashMap<String, HashMap<String, Location>>();
		Pm = new HashMap<String, Integer>();
		Pb = new HashMap<String, Boolean>();
		FirstJoin = new HashMap<String, Boolean>();
		Sign = new HashMap<String, HashMap<String, Boolean>>();
	}

	@SuppressWarnings("unchecked")
	public DataObj(Map<String, Object> map) {
		this.Back = (HashMap<String, Location>) repHashMapNull(map.get("Back"));
		this.Home = (HashMap<String, HashMap<String, Location>>) repHashMapNull(map.get("Home"));
		this.Spawn = (Location) map.get("Spawn");
		this.Warp = (HashMap<String, Location>) repHashMapNull(map.get("Warp"));
		this.PlayerOnline = (HashMap<String, HashMap<String, Location>>) repHashMapNull(map.get("PlayerOnline"));
		this.PlayerOffline = (HashMap<String, HashMap<String, Location>>) repHashMapNull(map.get("PlayerOffline"));
		this.Pm = (HashMap<String, Integer>) repHashMapNull(map.get("Pm"));
		this.Pb = (HashMap<String, Boolean>) repHashMapNull(map.get("Pb"));
		this.FirstJoin = (HashMap<String, Boolean>) repHashMapNull(map.get("FirstJoin"));
		this.Sign = (HashMap<String, HashMap<String, Boolean>>) repHashMapNull(map.get("Sign"));
		this.Antichat = (HashMap<String, HashMap<String, List<String>>>) repHashMapNull(map.get("Antichat"));
	}

	private Object repHashMapNull(Object object) {
		return object == null ? new HashMap<>() : object;
	}

	private HashMap<String, Location> Back;
	private HashMap<String, HashMap<String, Location>> Home;
	private Location Spawn;
	private HashMap<String, Location> Warp;
	private HashMap<String, HashMap<String, Location>> PlayerOnline;
	private HashMap<String, HashMap<String, Location>> PlayerOffline;
	private HashMap<String, Integer> Pm;
	private HashMap<String, Boolean> Pb;
	private HashMap<String, Boolean> FirstJoin;
	private HashMap<String, HashMap<String, Boolean>> Sign;
	private HashMap<String, HashMap<String, List<String>>> Antichat;

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Back", Back);
		map.put("Home", Home);
		map.put("Spawn", Spawn);
		map.put("Warp", Warp);
		map.put("PlayerOnline", PlayerOnline);
		map.put("PlayerOffline", PlayerOffline);
		map.put("Pm", Pm);
		map.put("Pb", Pb);
		map.put("FirstJoin", FirstJoin);
		map.put("Sign", Sign);
		map.put("Antichat", Antichat);
		return map;
	}

	/**
	 * @return back
	 */
	public final HashMap<String, Location> getBack() {
		return Back;
	}

	/**
	 * @param back 要设置的 back
	 */
	public final void setBack(HashMap<String, Location> back) {
		Back = back;
	}

	/**
	 * @return home
	 */
	public final HashMap<String, HashMap<String, Location>> getHome() {
		return (Home == null ? new HashMap<String, HashMap<String, Location>>() : Home);
	}

	/**
	 * @param home 要设置的 home
	 */
	public final void setHome(HashMap<String, HashMap<String, Location>> home) {
		Home = home;
	}

	/**
	 * @return spawn
	 */
	public final Location getSpawn() {
		return Spawn;
	}

	/**
	 * @param spawn 要设置的 spawn
	 */
	public final void setSpawn(Location spawn) {
		Spawn = spawn;
	}

	/**
	 * @return warp
	 */
	public final HashMap<String, Location> getWarp() {
		return Warp;
	}

	/**
	 * @param warp 要设置的 warp
	 */
	public final void setWarp(HashMap<String, Location> warp) {
		Warp = warp;
	}

	/**
	 * @return playerOffline
	 */
	public final HashMap<String, HashMap<String, Location>> getPlayerOffline() {
		return (PlayerOffline == null ? new HashMap<String, HashMap<String, Location>>() : PlayerOffline);
	}

	/**
	 * @param playerOffline 要设置的 playerOffline
	 */
	public final void setPlayerOffline(HashMap<String, HashMap<String, Location>> playerOffline) {
		PlayerOffline = playerOffline;
	}

	/**
	 * @return playerOnline
	 */
	public final HashMap<String, HashMap<String, Location>> getPlayerOnline() {
		return (PlayerOnline == null ? new HashMap<String, HashMap<String, Location>>() : PlayerOnline);
	}

	/**
	 * @param playerOnline 要设置的 playerOnline
	 */
	public final void setPlayerOnline(HashMap<String, HashMap<String, Location>> playerOnline) {
		PlayerOnline = playerOnline;
	}

	/**
	 * @return pm
	 */
	public final HashMap<String, Integer> getPm() {
		return Pm;
	}

	/**
	 * @param pm 要设置的 pm
	 */
	public final void setPm(HashMap<String, Integer> pm) {
		Pm = pm;
	}

	/**
	 * @return pb
	 */
	public final HashMap<String, Boolean> getPb() {
		return Pb;
	}

	/**
	 * @param pb 要设置的 pb
	 */
	public final void setPb(HashMap<String, Boolean> pb) {
		Pb = pb;
	}

	/**
	 * @return firstJoin
	 */
	public final HashMap<String, Boolean> getFirstJoin() {
		return FirstJoin;
	}

	/**
	 * @param firstJoin 要设置的 firstJoin
	 */
	public final void setFirstJoin(HashMap<String, Boolean> firstJoin) {
		FirstJoin = firstJoin;
	}

	/**
	 * @return sign
	 */
	public final HashMap<String, HashMap<String, Boolean>> getSign() {
		return (Sign == null ? new HashMap<String, HashMap<String, Boolean>>() : Sign);
	}

	/**
	 * @param sign 要设置的 sign
	 */
	public final void setSign(HashMap<String, HashMap<String, Boolean>> sign) {
		Sign = sign;
	}

	/**
	 * @return antichat
	 */
	public final HashMap<String, HashMap<String, List<String>>> getAntichat() {
		return (Antichat == null ? new HashMap<String, HashMap<String, List<String>>>() : Antichat);
	}

	/**
	 * @param antichat 要设置的 antichat
	 */
	public final void setAntichat(HashMap<String, HashMap<String, List<String>>> antichat) {
		Antichat = antichat;
	}

}
