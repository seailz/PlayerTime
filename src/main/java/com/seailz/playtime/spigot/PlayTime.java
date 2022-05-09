package com.seailz.playtime.spigot;

import com.seailz.playtime.discord.bot.Bot;
import com.seailz.playtime.spigot.commands.main.CommandMain;
import com.seailz.playtime.spigot.core.Locale;
import com.seailz.playtime.spigot.core.Logger;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import games.negative.framework.BasePlugin;
import games.negative.framework.util.Task;
import games.negative.framework.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
    @Getter
    private Bot bot;

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
            bot = new Bot(getConfig().getString("discord.token"));
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
        register(RegisterType.COMMAND);
        register(RegisterType.LISTENER);
        setDetails("PlayTime", "Seailz", "me.seailz.com", ChatColor.RED);

        Task.asyncDelayed(this, 600, () -> {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            String strDate = df.format(date);
           if (Calendar.DAY_OF_WEEK == 7 && strDate.equals("18:00")) {
               com.seailz.playtime.spigot.core.util.PlayTime.resetGlobalTime();
               Logger.log(Logger.LogLevel.SUCCESS, "Reset player times!");
           }
        });
        Task.asyncDelayed(this, 600, () -> {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            String strDate = df.format(date);
            if (strDate.equals("00:00")) {
                Bot b = getInstance().getBot();
                if (getInstance().getConfig().getString("bot.update-channel") == null) {
                    addError(true);
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    return;
                }

                String description = "";

                File[] files = new File(PlayTime.getInstance().getDataFolder() + "/data").listFiles();
                List<File> f = new ArrayList<>();
                Arrays.stream(files).forEach(file -> {
                    f.add(file);
                });

                f.sort((o1, o2) -> {
                    JSONUtil util = new JSONUtil(o1);
                    JSONUtil util2 = new JSONUtil(o2);

                    if (util.getLong("time") > util2.getLong("time")) {
                        return 1;
                    } else {
                        return 0;
                    }
                });

                int i = 0;
                for (File o : f) {
                    if (i == 10 || i == f.size()) break;
                    i++;
                    JSONUtil json = new JSONUtil(o);
                    String readable = TimeUtil.format(System.currentTimeMillis(),
                            json.getLong("time")
                    );
                    description = description + "\n#" + i + " | " + Bukkit.getOfflinePlayer(
                            o.getName().replaceAll(".json", "")
                    ).getName() + " - " + readable;

                }

                TextChannel c = b.getJda().getTextChannelById(getInstance().getConfig().getString("bot.update-channel"));
                c.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("Playtimes")
                                .setDescription(description)
                                .setColor(Color.CYAN)
                                .build()
                ).queue();
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
