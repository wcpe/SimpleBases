package com.wcpe.SimpleBases.Utils;

import com.wcpe.SimpleBases.Main;
import java.util.HashMap;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PapiHook extends PlaceholderExpansion {
    private Main a;
    private Plugin plugin;

    public PapiHook(Main main) {
        this.a = main;
    }

    public boolean reg() {
        return this.isRegistered();
    }

    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin("SimpleBases") != null;
    }

    public boolean register() {
        if (!this.canRegister()) {
            return false;
        } else {
            this.plugin = Bukkit.getPluginManager().getPlugin("SimpleBases");
            return this.plugin == null ? false : super.register();
        }
    }

    public String getAuthor() {
        return "wcpe";
    }

    public String getIdentifier() {
        return "SimpleBases";
    }

    public String getRequiredPlugin() {
        return "SimpleBases";
    }

    public String getVersion() {
        return "1.0.0";
    }

	public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        } else if (identifier.equals("sign")) {
            HashMap<String, Boolean> hashMap = (HashMap<String, Boolean>)this.a.getData().getSign().get(p.getName());
            return hashMap == null ? "0" : "" + hashMap.size();
        } else if (identifier.equals("pb")) {
            Boolean b = (Boolean)this.a.getData().getPb().get(p.getName());
            return b == null ? "false" : "" + b;
        } else if (identifier.equals("pm")) {
            int b = this.a.getData().getPm().get(p.getName()) != null ? (Integer)this.a.getData().getPm().get(p.getName()) : 0;
            return "" + b;
        } else {
            return null;
        }
    }
}
