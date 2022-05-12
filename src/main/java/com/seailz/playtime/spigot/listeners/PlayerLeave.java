package com.seailz.playtime.spigot.listeners;

import com.seailz.playtime.spigot.PlayTime;
import com.seailz.playtime.spigot.core.Logger;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (!e.getPlayer().hasPermission("time.tracker")) return;
        File folder = new File(PlayTime.getInstance().getDataFolder(), "/data");
        if (!folder.exists()) folder.mkdir();
        File file = new File(
                folder, e.getPlayer().getUniqueId() + ".json"
        );
        JSONUtil json =  new JSONUtil(file);
        PlayTime.getInstance().getPlayerFiles().put(e.getPlayer(), json);
        json.set("time", System.currentTimeMillis() - json.getLong("user.time"));
        json.save();
        json.reload();

        PlayTime.getInstance().getPlayerFiles().remove(e.getPlayer());
        Logger.log(Logger.LogLevel.DEBUG, e.getPlayer().getName() + " has quit! Saving their time...");
    }
}
