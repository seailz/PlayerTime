package com.seailz.playtime.spigot.listeners;

import com.seailz.playtime.spigot.PlayTime;
import com.seailz.playtime.spigot.core.Logger;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import com.seailz.playtime.spigot.core.util.profile.Profile;
import com.seailz.playtime.spigot.core.util.profile.ProfileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayerJoin implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("time.tracker")) return;
        File folder = new File(PlayTime.getInstance().getDataFolder(), "/data");
        if (!folder.exists()) folder.mkdir();
        File file = new File(
                folder, e.getPlayer().getUniqueId() + ".json"
        );
        JSONUtil json =  new JSONUtil(file);
        PlayTime.getInstance().getPlayerFiles().put(e.getPlayer(), json);
        json.set("time", System.currentTimeMillis());
        json.save();
        PlayTime.getInstance().getPlayerFiles().put(e.getPlayer(), json);
        Logger.log(Logger.LogLevel.DEBUG, e.getPlayer().getName() + " has joined! Saving their time...");
    }
}
