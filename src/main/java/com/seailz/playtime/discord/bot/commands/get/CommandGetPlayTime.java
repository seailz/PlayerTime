package com.seailz.playtime.discord.bot.commands.get;

import com.seailz.playtime.spigot.PlayTime;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import games.negative.framework.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.File;

public class CommandGetPlayTime extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String player = event.getOption("player").getAsString();
        if (!new File(PlayTime.getInstance().getDataFolder() + "/data",
                Bukkit.getPlayer(player).getUniqueId() + ".json"
        ).exists()) {
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Error!")
                            .setDescription("No data exists for that player")
                            .setColor(Color.RED)
                            .build()
            ).setEphemeral(true).queue();
            return;
        }

        File file = new File(PlayTime.getInstance().getDataFolder() + "/data",
                Bukkit.getPlayer(player).getUniqueId() + ".json"
        );

        JSONUtil json = new JSONUtil(
                file
        );

        String readable = TimeUtil.format(System.currentTimeMillis(),
                json.getLong("time")
        );

        event.replyEmbeds(
                new EmbedBuilder()
                        .setTitle("**PLAYTIME**")
                        .setDescription(
                                "**" + Bukkit.getPlayer(player).getName() + "** has **" + readable + "** of play time!"
                        )
                        .setColor(Color.CYAN)
                        .build()
        ).setEphemeral(true).queue();

         }
    }
