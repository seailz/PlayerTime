package com.seailz.playtime.spigot.core.util.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@Deprecated
public class Profile {
    @Getter
    private final OfflinePlayer player;
    @Getter
    @Setter
    private long time;
    @Getter
    private final String name;


    public Profile(OfflinePlayer player, String name) {
        this.player = player;
        this.name = name;
    }
}
