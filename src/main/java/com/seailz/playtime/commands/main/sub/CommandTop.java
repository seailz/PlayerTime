package com.seailz.playtime.commands.main.sub;

import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "top",
        description = "See the top players!"
)
public class CommandTop extends SubCommand {
    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {

    }
}
