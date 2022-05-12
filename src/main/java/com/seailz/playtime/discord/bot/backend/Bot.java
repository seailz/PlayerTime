package com.seailz.playtime.discord.bot.backend;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.awt.*;

@Getter
public abstract class Bot {

    private final JDA jda;

    public Bot(String token) throws LoginException {
        jda = JDABuilder.createDefault(token).build();
    }

    public void sendEmbed(String title, String description, Color color, TextChannel channel) {
        channel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .setColor(color)
                        .build()
        ).queue();
    }
}
