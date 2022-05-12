package com.seailz.playtime.spigot.core.util;

import com.seailz.playtime.discord.bot.backend.Bot;
import com.seailz.playtime.spigot.core.Logger;
import games.negative.framework.util.Task;
import games.negative.framework.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;

import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Watcher {
    public Watcher() {
        Task.asyncDelayed(com.seailz.playtime.spigot.PlayTime.getInstance(), 600, () -> {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            String strDate = df.format(date);
            if (Calendar.DAY_OF_WEEK == 7 && strDate.equals("18:00")) {
                com.seailz.playtime.spigot.core.util.PlayTime.resetGlobalTime();
                Logger.log(Logger.LogLevel.SUCCESS, "Reset player times!");
            }
        });
        Task.asyncDelayed(com.seailz.playtime.spigot.PlayTime.getInstance(), 600, () -> {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            String strDate = df.format(date);
            if (strDate.equals("00:00")) {
                Bot b = com.seailz.playtime.spigot.PlayTime.getInstance().getBot();
                if (com.seailz.playtime.spigot.PlayTime.getInstance().getConfig().getString("bot.update-channel") == null) {
                    com.seailz.playtime.spigot.PlayTime.getInstance().addError(true);
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    Logger.log(Logger.LogLevel.ERROR, "Channel ID is invalid, or Discord's servers are down!");
                    return;
                }

                String description = "";

                File[] files = new File(com.seailz.playtime.spigot.PlayTime.getInstance().getDataFolder() + "/data").listFiles();
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

                TextChannel c = b.getJda().getTextChannelById(com.seailz.playtime.spigot.PlayTime.getInstance().getConfig().getString("bot.update-channel"));
                c.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("Playtimes")
                                .setDescription(description)
                                .setColor(Color.CYAN)
                                .build()
                ).queue();
            }
        });
    }
}
