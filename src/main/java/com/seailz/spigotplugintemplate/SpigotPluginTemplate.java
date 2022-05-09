package com.seailz.spigotplugintemplate;

import com.seailz.spigotplugintemplate.commands.main.CommandMain;
import com.seailz.spigotplugintemplate.core.Locale;
import com.seailz.spigotplugintemplate.core.Logger;
import games.negative.framework.BasePlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public final class SpigotPluginTemplate extends BasePlugin {

    @Getter
    @Setter
    public static SpigotPluginTemplate instance;
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
        setDetails("Plugin", "Seailz", "me.seailz.com", ChatColor.RED);

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
