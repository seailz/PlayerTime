package com.seailz.playtime.discord.bot;

import com.seailz.playtime.discord.bot.backend.Bot;
import com.seailz.playtime.discord.bot.commands.get.CommandGetPlayTime;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.security.auth.login.LoginException;

public class PlayTimeBot extends Bot {
    private JDA jda = this.getJda();

    public PlayTimeBot(String token) throws LoginException {
        super(token);
        jda.upsertCommand("getplaytime", "Get a player's play time!")
                .addOption(OptionType.STRING, "player", "The Player's name")
                .queue();
        jda.addEventListener(new CommandGetPlayTime());
    }


}
