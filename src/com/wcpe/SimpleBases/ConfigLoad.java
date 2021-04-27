package com.wcpe.SimpleBases;

import java.util.ArrayList;
import java.util.HashMap;

import com.wcpe.SimpleBases.Obj.ChatFormatObj;
import com.wcpe.SimpleBases.Obj.ChatObj;
import com.wcpe.SimpleBases.Obj.ConfigObj;

public class ConfigLoad {
	@SuppressWarnings("serial")
	public ConfigLoad(Main a) {
		a.reloadConfig();
		HashMap<String, Object> atPlayer = new HashMap<String, Object>();
		atPlayer.put("Enable", a.getConfig().getBoolean("AtPlayer.Enable"));
		atPlayer.put("CheckAt", a.getConfig().getString("AtPlayer.CheckAt"));
		atPlayer.put("CheckAtAll", a.getConfig().getString("AtPlayer.CheckAtAll"));
		atPlayer.put("replaceAll", a.getConfig().getString("AtPlayer.replaceAll"));
		atPlayer.put("AtAll", a.getConfig().getString("AtPlayer.AtAll"));
		atPlayer.put("Ated", a.getConfig().getString("AtPlayer.Ated"));
		atPlayer.put("AtedTitle", a.getConfig().getString("AtPlayer.AtedTitle"));
		atPlayer.put("At", a.getConfig().getString("AtPlayer.At"));
		atPlayer.put("AtTitle", a.getConfig().getString("AtPlayer.AtTitle"));
		atPlayer.put("ShieldOpen", a.getConfig().getString("AtPlayer.ShieldOpen"));

		HashMap<String, Object> Menu = new HashMap<String, Object>();
		Menu.put("Enable", a.getConfig().getBoolean("Menu.Enable"));
		Menu.put("Type", a.getConfig().getString("Menu.Type"));
		Menu.put("Name", a.getConfig().getString("Menu.Name"));
		Menu.put("Slot", a.getConfig().getInt("Menu.Slot"));
		Menu.put("Lore", a.getConfig().getStringList("Menu.Lore"));

		HashMap<String, Object> Sign = new HashMap<String, Object>();
		Sign.put("Enable", a.getConfig().getBoolean("Sign.Enable"));
		Sign.put("Title", a.getConfig().getString("Sign.Title"));
		Sign.put("BeforeSignType", a.getConfig().getString("Sign.BeforeSignType"));
		Sign.put("BeforeSignLore", a.getConfig().getStringList("Sign.BeforeSignLore"));
		Sign.put("AfterSignType", a.getConfig().getString("Sign.AfterSignType"));
		Sign.put("AfterSignLore", a.getConfig().getStringList("Sign.AfterSignLore"));
		Sign.put("oldSignType", a.getConfig().getString("Sign.oldSignType"));
		Sign.put("oldSignLore", a.getConfig().getStringList("Sign.oldSignLore"));
		Sign.put("dataFormat", a.getConfig().getString("Sign.dataFormat"));
		Sign.put("RightDownTipName", a.getConfig().getString("Sign.RightDownTipName"));
		Sign.put("RightDownTipType", a.getConfig().getString("Sign.RightDownTipType"));
		Sign.put("RightDownTipLore", a.getConfig().getStringList("Sign.RightDownTipLore"));
		Sign.put("DailyReward", a.getConfig().getStringList("Sign.DailyReward"));

		HashMap<String, Object> antiSpawnWorld = new HashMap<String, Object>();
		antiSpawnWorld.put("Enable", a.getConfig().getBoolean("AntiSpawnWorld.Enable"));
		antiSpawnWorld.put("Worlds", a.getConfig().getStringList("AntiSpawnWorld.Worlds"));

		HashMap<String, Object> clear = new HashMap<String, Object>();
		clear.put("Enable", a.getConfig().getBoolean("Clear.Enable"));
		clear.put("ClearDropNumber", a.getConfig().getInt("Clear.ClearDropNumber"));
		clear.put("ClearMobsNumber", a.getConfig().getInt("Clear.ClearMobsNumber"));
		clear.put("ClearCooling", a.getConfig().getInt("Clear.ClearCooling"));
		clear.put("ClearCountDown", a.getConfig().getInt("Clear.ClearCountDown"));
		clear.put("ClearList", a.getConfig().getStringList("Clear.ClearList"));

		HashMap<String, Object> chatFormat = new HashMap<String, Object>();
		chatFormat.put("Enable", a.getConfig().getBoolean("ChatFormat.Enable"));
		chatFormat.put("CoolFinishTipEnable", a.getConfig().getBoolean("ChatFormat.CoolFinishTipEnable"));
		chatFormat.put("Chat", new ArrayList<ChatObj>() {
			{
				for (String s : a.getConfig().getConfigurationSection("ChatFormat.Chat").getKeys(false)) {
					add(new ChatObj(a.getConfig().getString("ChatFormat.Chat." + s + ".Permission"),
							a.getConfig().getString("ChatFormat.Chat." + s + ".Format"),
							a.getConfig().getInt("ChatFormat.Chat." + s + ".ChatLength"),
							a.getConfig().getString("ChatFormat.Chat." + s + ".ChatLengthMessage"),
							a.getConfig().getInt("ChatFormat.Chat." + s + ".ChatCool"),
							a.getConfig().getString("ChatFormat.Chat." + s + ".ChatCoolMessage"),
							a.getConfig().getInt("ChatFormat.Chat." + s + ".Weight"),
							a.getConfig().getStringList("ChatFormat.Chat." + s + ".ReplaceAll"),
							a.getConfig().getBoolean("ChatFormat.Chat." + s + ".IgnoreCool"),
							a.getConfig().getBoolean("ChatFormat.Chat." + s + ".IgnoreLength"),
							a.getConfig().getBoolean("ChatFormat.Chat." + s + ".IgnoreReplaceAll")));
				}
			}
		});
		chatFormat.put("Format", new HashMap<String, ChatFormatObj>() {
			{
				for (String s : a.getConfig().getConfigurationSection("ChatFormat.Format").getKeys(false)) {
					put(s, new ChatFormatObj(a.getConfig().getString("ChatFormat.Format." + s + ".Message"),
							a.getConfig().getString("ChatFormat.Format." + s + ".Command"),
							a.getConfig().getString("ChatFormat.Format." + s + ".List")));
				}
			}
		});

		HashMap<String, Object> checkVersion = new HashMap<String, Object>();
		checkVersion.put("Enable", a.getConfig().getBoolean("CheckVersion.Enable"));
		checkVersion.put("Time", a.getConfig().getLong("CheckVersion.Time"));

		HashMap<String, Object> firstJoinCommands = new HashMap<String, Object>();
		firstJoinCommands.put("Enable", a.getConfig().getBoolean("FirstJoinCommands.Enable"));
		firstJoinCommands.put("Commands", a.getConfig().getStringList("FirstJoinCommands.Commands"));

		HashMap<String, Object> joinSendMessage = new HashMap<String, Object>();

		HashMap<String, Object> Title = new HashMap<String, Object>();
		Title.put("Enable", a.getConfig().getBoolean("JoinSendMessage.Title.Enable"));
		Title.put("main", a.getConfig().getString("JoinSendMessage.Title.main"));
		Title.put("sub", a.getConfig().getString("JoinSendMessage.Title.sub"));
		Title.put("fadein", a.getConfig().getInt("JoinSendMessage.Title.fadein"));
		Title.put("fadeout", a.getConfig().getInt("JoinSendMessage.Title.fadeout"));
		Title.put("stay", a.getConfig().getInt("JoinSendMessage.Title.stay"));

		HashMap<String, Object> Message = new HashMap<String, Object>();
		Message.put("Enable", a.getConfig().getBoolean("JoinSendMessage.Message.Enable"));
		Message.put("Send", a.getConfig().getStringList("JoinSendMessage.Message.Send"));

		HashMap<String, Object> Actionbar = new HashMap<String, Object>();
		Actionbar.put("Enable", a.getConfig().getBoolean("JoinSendMessage.Actionbar.Enable"));
		Actionbar.put("Send", a.getConfig().getString("JoinSendMessage.Actionbar.Send"));

		joinSendMessage.put("Title", Title);
		joinSendMessage.put("Message", Message);
		joinSendMessage.put("Actionbar", Actionbar);

		HashMap<String, Object> joinQuitMessage = new HashMap<String, Object>();
		joinQuitMessage.put("Enable", a.getConfig().getBoolean("JoinQuitMessage.Enable"));
		joinQuitMessage.put("OpJoin", a.getConfig().getString("JoinQuitMessage.OpJoin"));
		joinQuitMessage.put("NoOpJoin", a.getConfig().getString("JoinQuitMessage.NoOpJoin"));
		joinQuitMessage.put("OpQuit", a.getConfig().getString("JoinQuitMessage.OpQuit"));
		joinQuitMessage.put("NoOpQuit", a.getConfig().getString("JoinQuitMessage.NoOpQuit"));

		HashMap<String, Object> playerGameTime = new HashMap<String, Object>();
		playerGameTime.put("Enable", a.getConfig().getBoolean("PlayerGameTime.Enable"));
		playerGameTime.put("Type", a.getConfig().getString("PlayerGameTime.Type"));
		playerGameTime.put("Command", a.getConfig().getStringList("PlayerGameTime.Command"));
		playerGameTime.put("Message", a.getConfig().getStringList("PlayerGameTime.Message"));

		HashMap<String, Object> tpa = new HashMap<String, Object>();
		tpa.put("Enable", a.getConfig().getBoolean("Tpa.Enable"));
		tpa.put("CountDown", a.getConfig().getInt("Tpa.CountDown"));
		tpa.put("Cooling", a.getConfig().getInt("Tpa.Cooling"));

		HashMap<String, Object> back = new HashMap<String, Object>();
		back.put("Enable", a.getConfig().getBoolean("Back.Enable"));
		back.put("CountDown", a.getConfig().getInt("Back.CountDown"));
		back.put("Cooling", a.getConfig().getInt("Back.Cooling"));

		HashMap<String, Object> spawn = new HashMap<String, Object>();
		spawn.put("Enable", a.getConfig().getBoolean("Spawn.Enable"));
		spawn.put("CountDown", a.getConfig().getInt("Spawn.CountDown"));
		spawn.put("Cooling", a.getConfig().getInt("Spawn.Cooling"));

		HashMap<String, Object> home = new HashMap<String, Object>();
		home.put("Enable", a.getConfig().getBoolean("Home.Enable"));
		home.put("Number", a.getConfig().getInt("Home.Number"));
		home.put("CountDown", a.getConfig().getInt("Home.CountDown"));
		home.put("Cooling", a.getConfig().getInt("Home.Cooling"));

		HashMap<String, Object> gamemode = new HashMap<String, Object>();
		gamemode.put("Enable", a.getConfig().getBoolean("Gamemode.Enable"));

		HashMap<String, Object> warp = new HashMap<String, Object>();
		warp.put("Enable", a.getConfig().getBoolean("Warp.Enable"));

		HashMap<String, Object> fly = new HashMap<String, Object>();
		fly.put("Enable", a.getConfig().getBoolean("Fly.Enable"));

		HashMap<String, Object> gc = new HashMap<String, Object>();
		gc.put("Enable", a.getConfig().getBoolean("Gc.Enable"));

		HashMap<String, Object> hat = new HashMap<String, Object>();
		hat.put("Enable", a.getConfig().getBoolean("Hat.Enable"));

		HashMap<String, Object> playcmd = new HashMap<String, Object>();
		playcmd.put("Enable", a.getConfig().getBoolean("Playcmd.Enable"));

		HashMap<String, Object> speed = new HashMap<String, Object>();
		speed.put("Enable", a.getConfig().getBoolean("Speed.Enable"));

		HashMap<String, Object> tponline = new HashMap<String, Object>();
		tponline.put("Enable", a.getConfig().getBoolean("Tponline.Enable"));
		tponline.put("SaveNumber", a.getConfig().getInt("Tponline.SaveNumber"));

		HashMap<String, Object> tpoffline = new HashMap<String, Object>();
		tpoffline.put("Enable", a.getConfig().getBoolean("Tpoffline.Enable"));
		tpoffline.put("SaveNumber", a.getConfig().getInt("Tpoffline.SaveNumber"));

		HashMap<String, Object> zisha = new HashMap<String, Object>();
		zisha.put("Enable", a.getConfig().getBoolean("Zisha.Enable"));

		this.setting = new ConfigObj(atPlayer, Menu, Sign, antiSpawnWorld, clear, chatFormat, checkVersion,
				firstJoinCommands, joinSendMessage, joinQuitMessage, playerGameTime, tpa, back, spawn, home, gamemode,
				warp, fly, gc, hat, playcmd, speed, tponline, tpoffline, zisha);
	}

	private ConfigObj setting;

	/**
	 * @return setting
	 */
	public final ConfigObj getSetting() {
		return setting;
	}

}
