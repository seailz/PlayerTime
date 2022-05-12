package com.seailz.playtime.spigot;

import com.seailz.playtime.discord.bot.PlayTimeBot;
import com.seailz.playtime.discord.bot.backend.Bot;
import com.seailz.playtime.spigot.commands.main.CommandMain;
import com.seailz.playtime.spigot.core.Locale;
import com.seailz.playtime.spigot.core.Logger;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import com.seailz.playtime.spigot.core.util.profile.Profile;
import com.seailz.playtime.spigot.core.util.Watcher;
import com.seailz.playtime.spigot.core.util.profile.ProfileManager;
import com.seailz.playtime.spigot.listeners.PlayerJoin;
import com.seailz.playtime.spigot.listeners.PlayerLeave;
import games.negative.framework.BasePlugin;
import games.negative.framework.util.cache.ObjectCache;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import javax.security.auth.login.LoginException;
import java.util.*;

public final class PlayTime extends BasePlugin {

    @Getter @Setter public static PlayTime instance;
    @Getter boolean debug;
    @Getter @Setter private int minorErrors;
    @Getter @Setter private int severeErrors;
    @Getter private ArrayList<String> debugLog;
    @Getter @Setter private String pluginName;
    @Getter @Setter private String developer;
    @Getter @Setter private String URL = null;
    @Getter @Setter private ChatColor color;
    @Getter private Bot bot;
    @Getter @Setter private ObjectCache<Profile> json;
    @Getter private HashMap<Player, JSONUtil> playerFiles = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        long start = System.currentTimeMillis();

        setInstance(this);
        Locale.init(this);

        setMinorErrors(0);
        setSevereErrors(0);

        debugLog = new ArrayList<>();
        this.debug = getConfig().getBoolean("debug");
        saveDefaultConfig();

        try {
            bot = new PlayTimeBot(getConfig().getString("discord.token"));
        } catch (LoginException e) {
            Logger.log(Logger.LogLevel.ERROR, "Token is invalid, or Discord's servers are down!");
            Logger.log(Logger.LogLevel.ERROR, "Token is invalid, or Discord's servers are down!");
            Logger.log(Logger.LogLevel.ERROR, "Token is invalid, or Discord's servers are down!");
            addError(true);
            getDebugLog().add("Invalid bot ID!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set details and register things
        setDetails("PlayTime", "Seailz", "me.seailz.com", ChatColor.RED);
        register(RegisterType.COMMAND);
        register(RegisterType.LISTENER);
        register(RegisterType.WATCHER);

        long finish = System.currentTimeMillis() - start;
        Logger.log(Logger.LogLevel.SUCCESS, "Started in " + String.valueOf(finish) + "ms!");
    }

    public void addError(boolean severe) {
        if (severe) {
            severeErrors++;
        } else {
            minorErrors++;
        }
    }

    public void register(RegisterType type) {
        switch (type) {
            case COMMAND:
                registerCommands(
                        // Insert commands
                        new CommandMain()
                );
                break;
            case LISTENER:
                registerListeners(
                        new PlayerJoin(),
                        new PlayerLeave()
                );
                break;
            case WATCHER:
                new Watcher();
                break;
        }
    }

    public void setDetails(String pluginName, String developer, String URL, ChatColor color) {
        setPluginName(pluginName);
        setDeveloper(developer);
        setURL(URL);
        setColor(color);
    }

    public void setDetails(String pluginName, String developer, ChatColor color) {
        setPluginName(pluginName);
        setDeveloper(developer);
        setURL(URL);
        setColor(color);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public enum RegisterType {COMMAND, LISTENER, WATCHER, JSON}
}
