package com.seailz.playtime.discord.bot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import com.seailz.playtime.discord.bot.commands.get.CommandGetPlayTime;

@Getter
public class Bot {

    private final JDA jda;

    public Bot(String token) throws LoginException {
        jda = JDABuilder.createDefault(token).build();
        jda.upsertCommand("getplaytime", "Get a player's play time!")
                .addOption(OptionType.STRING, "player", "The Player's name")
                .queue();
        jda.addEventListener(new CommandGetPlayTime());
    }
}
