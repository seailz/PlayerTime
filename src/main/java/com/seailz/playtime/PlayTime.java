package com.seailz.playtime;

import com.seailz.playtime.commands.main.CommandMain;
import com.seailz.playtime.core.Locale;
import com.seailz.playtime.core.Logger;
import com.seailz.playtime.core.util.JSONUtil;
import games.negative.framework.BasePlugin;
import games.negative.framework.util.Task;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class PlayTime extends BasePlugin {

    @Getter
    @Setter
    public static PlayTime instance;
    @Getter
    boolean debug;
    @Getter
    @Setter
    private int minorErrors;
    @Getter
    @Setter
    private int severeErrors;
    @Getter
    private ArrayList<String> debugLog;
    @Getter
    @Setter
    private String pluginName;
    @Getter
    @Setter
    private String developer;
    @Getter
    @Setter
    private String URL = null;
    @Getter
    @Setter
    private ChatColor color;
    @Getter
    private HashMap<Player, JSONUtil> playerFiles = new HashMap<>();

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

        // Set details and register things
        register(RegisterType.COMMAND);
        register(RegisterType.LISTENER);
        setDetails("PlayTime", "Seailz", "me.seailz.com", ChatColor.RED);

        Task.asyncDelayed(this, 100, () -> {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            String strDate = df.format(date);
           if (Calendar.DAY_OF_WEEK == 7 && strDate.equals("18:00")) {
               com.seailz.playtime.core.util.PlayTime.resetGlobalTime();
               Logger.log(Logger.LogLevel.SUCCESS, "Reset player times!");
           }
        });

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
                        // Register Listeners
                );
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


    public enum RegisterType {COMMAND, LISTENER}
}
