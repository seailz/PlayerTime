package com.seailz.playtime.spigot.commands.main.sub;

import com.seailz.playtime.spigot.PlayTime;
import com.seailz.playtime.spigot.core.Locale;
import com.seailz.playtime.spigot.core.util.JSONUtil;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;

@CommandInfo(
        name = "get",
        args = {"player"},
        shortCommands = {"getbal", "time"}
)
public class CommandGet extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!new File(PlayTime.getInstance().getDataFolder() + "/data",
                    Bukkit.getPlayer(args[0]).getUniqueId() + ".json"
                ).exists()) {
            Locale.NO_DATA.send(sender);
        }

        File file = new File(PlayTime.getInstance().getDataFolder() + "/data",
                Bukkit.getPlayer(args[0]).getUniqueId() + ".json"
        );

        JSONUtil json = new JSONUtil(
                file
        );

        String readable = TimeUtil.format(System.currentTimeMillis(),
                json.getLong("time")
        );

        Locale.TIME_OTHER
                .replace(
                        "%player%",
                        Bukkit.getPlayer(args[0]).getName()
                ).replace(
                        "%time%",
                        readable
                ).send(sender);

    }
}
