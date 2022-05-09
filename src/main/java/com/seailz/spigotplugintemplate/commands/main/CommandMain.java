package com.seailz.spigotplugintemplate.commands.main;

import com.seailz.spigotplugintemplate.SpigotPluginTemplate;
import com.seailz.spigotplugintemplate.commands.main.sub.CommandReport;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.message.Message;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "main",
        playerOnly = true
)
public class CommandMain extends Command {

    String color = "&" + SpigotPluginTemplate.getInstance().getColor().getChar();
    String name = SpigotPluginTemplate.getInstance().getPluginName();
    String author = SpigotPluginTemplate.getInstance().getDeveloper();
    String url;

    public CommandMain() {
        this.addSubCommands(new CommandReport());
        setName(SpigotPluginTemplate.instance.getPluginName().replaceAll(" ", ""));
        if (SpigotPluginTemplate.getInstance().getURL() == null) url = SpigotPluginTemplate.getInstance().getURL();
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
