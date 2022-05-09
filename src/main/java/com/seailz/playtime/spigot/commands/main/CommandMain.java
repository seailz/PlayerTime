package com.seailz.playtime.spigot.commands.main;

import com.seailz.playtime.spigot.PlayTime;
import com.seailz.playtime.spigot.commands.main.sub.CommandReport;
import com.seailz.playtime.spigot.commands.main.sub.CommandTop;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.message.Message;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "main",
        playerOnly = true
)
public class CommandMain extends Command {

    String color = "&" + PlayTime.getInstance().getColor().getChar();
    String name = PlayTime.getInstance().getPluginName();
    String author = PlayTime.getInstance().getDeveloper();
    String url;

    public CommandMain() {
        this.addSubCommands(
                new CommandReport(),
                new CommandTop()
        );
        setName(PlayTime.instance.getPluginName().replaceAll(" ", ""));
        if (PlayTime.getInstance().getURL() == null) url = PlayTime.getInstance().getURL();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (url == null) {
            new Message(
                    color + "&m------------------------",
                    "&f        " + name,
                    "&f  Developed by " + color + author,
                    color + "&m------------------------"
            ).send(sender);
            return;
        }
            new Message(
                    color + "&m------------------------",
                    "&f        " + name,
                    "&f  Developed by " + color + author,
                    color + "     " + url,
                    color + "&m------------------------"
            ).send(sender);
    }
}
