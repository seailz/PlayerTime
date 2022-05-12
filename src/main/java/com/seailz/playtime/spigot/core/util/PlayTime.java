package com.seailz.playtime.spigot.core.util;

import games.negative.framework.util.cache.ObjectCache;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;

public class PlayTime {
    private final Player p;

    public PlayTime(Player p) {
        this.p = p;
    }

    public File getFile() {
        return null;
    }

    public void resetTime() {
        File file = new File(com.seailz.playtime.spigot.PlayTime.getInstance().getDataFolder(), p.getUniqueId() + ".json");
        file.delete();
    }

    public static void resetGlobalTime() {
        Arrays.stream(
                new File(com.seailz.playtime.spigot.PlayTime.getInstance().getDataFolder() + ".data").listFiles()
        ).forEach(file -> {
            file.delete();
        });
    }
}
