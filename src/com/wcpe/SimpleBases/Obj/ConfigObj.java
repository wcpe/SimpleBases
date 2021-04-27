package com.wcpe.SimpleBases.Obj;

import java.util.HashMap;

public class ConfigObj {
	public ConfigObj(HashMap<String, Object> atPlayer, HashMap<String, Object> menu, HashMap<String, Object> sign,
			HashMap<String, Object> antiSpawnWorld, HashMap<String, Object> clear, HashMap<String, Object> chatFormat,
			HashMap<String, Object> checkVersion, HashMap<String, Object> firstJoinCommands,
			HashMap<String, Object> joinSendMessage, HashMap<String, Object> joinQuitMessage,
			HashMap<String, Object> playerGameTime, HashMap<String, Object> tpa, HashMap<String, Object> back,
			HashMap<String, Object> spawn, HashMap<String, Object> home, HashMap<String, Object> gamemode,
			HashMap<String, Object> warp, HashMap<String, Object> fly, HashMap<String, Object> gc,
			HashMap<String, Object> hat, HashMap<String, Object> playcmd, HashMap<String, Object> speed,
			HashMap<String, Object> tponline, HashMap<String, Object> tpoffline, HashMap<String, Object> zisha) {
		super();
		AtPlayer = atPlayer;
		Menu = menu;
		Sign = sign;
		AntiSpawnWorld = antiSpawnWorld;
		Clear = clear;
		ChatFormat = chatFormat;
		CheckVersion = checkVersion;
		FirstJoinCommands = firstJoinCommands;
		JoinSendMessage = joinSendMessage;
		JoinQuitMessage = joinQuitMessage;
		PlayerGameTime = playerGameTime;
		Tpa = tpa;
		Back = back;
		Spawn = spawn;
		Home = home;
		Gamemode = gamemode;
		Warp = warp;
		Fly = fly;
		Gc = gc;
		Hat = hat;
		Playcmd = playcmd;
		Speed = speed;
		Tponline = tponline;
		Tpoffline = tpoffline;
		Zisha = zisha;
	}

	private HashMap<String, Object> AtPlayer = new HashMap<String, Object>();
	private HashMap<String, Object> Menu = new HashMap<String, Object>();
	private HashMap<String, Object> Sign = new HashMap<String, Object>();
	private HashMap<String, Object> AntiSpawnWorld = new HashMap<String, Object>();
	private HashMap<String, Object> Clear = new HashMap<String, Object>();
	private HashMap<String, Object> ChatFormat = new HashMap<String, Object>();
	private HashMap<String, Object> CheckVersion = new HashMap<String, Object>();
	private HashMap<String, Object> FirstJoinCommands = new HashMap<String, Object>();
	private HashMap<String, Object> JoinSendMessage = new HashMap<String, Object>();
	private HashMap<String, Object> JoinQuitMessage = new HashMap<String, Object>();
	private HashMap<String, Object> PlayerGameTime = new HashMap<String, Object>();
	private HashMap<String, Object> Tpa = new HashMap<String, Object>();
	private HashMap<String, Object> Back = new HashMap<String, Object>();
	private HashMap<String, Object> Spawn = new HashMap<String, Object>();
	private HashMap<String, Object> Home = new HashMap<String, Object>();
	private HashMap<String, Object> Gamemode = new HashMap<String, Object>();
	private HashMap<String, Object> Warp = new HashMap<String, Object>();
	private HashMap<String, Object> Fly = new HashMap<String, Object>();
	private HashMap<String, Object> Gc = new HashMap<String, Object>();
	private HashMap<String, Object> Hat = new HashMap<String, Object>();
	private HashMap<String, Object> Playcmd = new HashMap<String, Object>();
	private HashMap<String, Object> Speed = new HashMap<String, Object>();
	private HashMap<String, Object> Zisha = new HashMap<String, Object>();
	private HashMap<String, Object> Tponline = new HashMap<String, Object>();
	private HashMap<String, Object> Tpoffline = new HashMap<String, Object>();

	/**
	 * @return atPlayer
	 */
	public final HashMap<String, Object> getAtPlayer() {
		return AtPlayer;
	}

	/**
	 * @param atPlayer 要设置的 atPlayer
	 */
	public final void setAtPlayer(HashMap<String, Object> atPlayer) {
		AtPlayer = atPlayer;
	}

	/**
	 * @return holographicDisplays
	 */

	/**
	 * @return antiSpawnWorld
	 */
	public final HashMap<String, Object> getAntiSpawnWorld() {
		return AntiSpawnWorld;
	}

	/**
	 * @param antiSpawnWorld 要设置的 antiSpawnWorld
	 */
	public final void setAntiSpawnWorld(HashMap<String, Object> antiSpawnWorld) {
		AntiSpawnWorld = antiSpawnWorld;
	}

	/**
	 * @return clear
	 */
	public final HashMap<String, Object> getClear() {
		return Clear;
	}

	/**
	 * @param clear 要设置的 clear
	 */
	public final void setClear(HashMap<String, Object> clear) {
		Clear = clear;
	}

	/**
	 * @return chatFormat
	 */
	public final HashMap<String, Object> getChatFormat() {
		return ChatFormat;
	}

	/**
	 * @param chatFormat 要设置的 chatFormat
	 */
	public final void setChatFormat(HashMap<String, Object> chatFormat) {
		ChatFormat = chatFormat;
	}

	/**
	 * @return checkVersion
	 */
	public final HashMap<String, Object> getCheckVersion() {
		return CheckVersion;
	}

	/**
	 * @param checkVersion 要设置的 checkVersion
	 */
	public final void setCheckVersion(HashMap<String, Object> checkVersion) {
		CheckVersion = checkVersion;
	}

	/**
	 * @return firstJoinCommands
	 */
	public final HashMap<String, Object> getFirstJoinCommands() {
		return FirstJoinCommands;
	}

	/**
	 * @param firstJoinCommands 要设置的 firstJoinCommands
	 */
	public final void setFirstJoinCommands(HashMap<String, Object> firstJoinCommands) {
		FirstJoinCommands = firstJoinCommands;
	}

	/**
	 * @return joinSendMessage
	 */
	public final HashMap<String, Object> getJoinSendMessage() {
		return JoinSendMessage;
	}

	/**
	 * @param joinSendMessage 要设置的 joinSendMessage
	 */
	public final void setJoinSendMessage(HashMap<String, Object> joinSendMessage) {
		JoinSendMessage = joinSendMessage;
	}

	/**
	 * @return joinQuitMessage
	 */
	public final HashMap<String, Object> getJoinQuitMessage() {
		return JoinQuitMessage;
	}

	/**
	 * @param joinQuitMessage 要设置的 joinQuitMessage
	 */
	public final void setJoinQuitMessage(HashMap<String, Object> joinQuitMessage) {
		JoinQuitMessage = joinQuitMessage;
	}

	/**
	 * @return playerGameTime
	 */
	public final HashMap<String, Object> getPlayerGameTime() {
		return PlayerGameTime;
	}

	/**
	 * @param playerGameTime 要设置的 playerGameTime
	 */
	public final void setPlayerGameTime(HashMap<String, Object> playerGameTime) {
		PlayerGameTime = playerGameTime;
	}

	/**
	 * @return tpa
	 */
	public final HashMap<String, Object> getTpa() {
		return Tpa;
	}

	/**
	 * @param tpa 要设置的 tpa
	 */
	public final void setTpa(HashMap<String, Object> tpa) {
		Tpa = tpa;
	}

	/**
	 * @return back
	 */
	public final HashMap<String, Object> getBack() {
		return Back;
	}

	/**
	 * @param back 要设置的 back
	 */
	public final void setBack(HashMap<String, Object> back) {
		Back = back;
	}

	/**
	 * @return spawn
	 */
	public final HashMap<String, Object> getSpawn() {
		return Spawn;
	}

	/**
	 * @param spawn 要设置的 spawn
	 */
	public final void setSpawn(HashMap<String, Object> spawn) {
		Spawn = spawn;
	}

	/**
	 * @return home
	 */
	public final HashMap<String, Object> getHome() {
		return Home;
	}

	/**
	 * @param home 要设置的 home
	 */
	public final void setHome(HashMap<String, Object> home) {
		Home = home;
	}

	/**
	 * @return gamemode
	 */
	public final HashMap<String, Object> getGamemode() {
		return Gamemode;
	}

	/**
	 * @param gamemode 要设置的 gamemode
	 */
	public final void setGamemode(HashMap<String, Object> gamemode) {
		Gamemode = gamemode;
	}

	/**
	 * @return warp
	 */
	public final HashMap<String, Object> getWarp() {
		return Warp;
	}

	/**
	 * @param warp 要设置的 warp
	 */
	public final void setWarp(HashMap<String, Object> warp) {
		Warp = warp;
	}

	/**
	 * @return menu
	 */
	public final HashMap<String, Object> getMenu() {
		return Menu;
	}

	/**
	 * @param menu 要设置的 menu
	 */
	public final void setMenu(HashMap<String, Object> menu) {
		Menu = menu;
	}

	/**
	 * @return sign
	 */
	public final HashMap<String, Object> getSign() {
		return Sign;
	}

	/**
	 * @param sign 要设置的 sign
	 */
	public final void setSign(HashMap<String, Object> sign) {
		Sign = sign;
	}

	/**
	 * @return fly
	 */
	public final HashMap<String, Object> getFly() {
		return Fly;
	}

	/**
	 * @param fly 要设置的 fly
	 */
	public final void setFly(HashMap<String, Object> fly) {
		Fly = fly;
	}

	/**
	 * @return gc
	 */
	public final HashMap<String, Object> getGc() {
		return Gc;
	}

	/**
	 * @param gc 要设置的 gc
	 */
	public final void setGc(HashMap<String, Object> gc) {
		Gc = gc;
	}

	/**
	 * @return hat
	 */
	public final HashMap<String, Object> getHat() {
		return Hat;
	}

	/**
	 * @param hat 要设置的 hat
	 */
	public final void setHat(HashMap<String, Object> hat) {
		Hat = hat;
	}

	/**
	 * @return playcmd
	 */
	public final HashMap<String, Object> getPlaycmd() {
		return Playcmd;
	}

	/**
	 * @param playcmd 要设置的 playcmd
	 */
	public final void setPlaycmd(HashMap<String, Object> playcmd) {
		Playcmd = playcmd;
	}

	/**
	 * @return speed
	 */
	public final HashMap<String, Object> getSpeed() {
		return Speed;
	}

	/**
	 * @param speed 要设置的 speed
	 */
	public final void setSpeed(HashMap<String, Object> speed) {
		Speed = speed;
	}

	/**
	 * @return zisha
	 */
	public final HashMap<String, Object> getZisha() {
		return Zisha;
	}

	/**
	 * @param zisha 要设置的 zisha
	 */
	public final void setZisha(HashMap<String, Object> zisha) {
		Zisha = zisha;
	}

	/**
	 * @return tponline
	 */
	public final HashMap<String, Object> getTponline() {
		return Tponline;
	}

	/**
	 * @param tponline 要设置的 tponline
	 */
	public final void setTponline(HashMap<String, Object> tponline) {
		Tponline = tponline;
	}

	/**
	 * @return tpoffline
	 */
	public final HashMap<String, Object> getTpoffline() {
		return Tpoffline;
	}

	/**
	 * @param tpoffline 要设置的 tpoffline
	 */
	public final void setTpoffline(HashMap<String, Object> tpoffline) {
		Tpoffline = tpoffline;
	}

}