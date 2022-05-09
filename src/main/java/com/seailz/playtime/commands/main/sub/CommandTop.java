package com.seailz.playtime.commands.main.sub;

import com.seailz.playtime.PlayTime;
import com.seailz.playtime.core.Locale;
import com.seailz.playtime.core.util.JSONUtil;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.*;

@CommandInfo(
        name = "top",
        description = "See the top players!"
)
public class CommandTop extends SubCommand {
    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
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
            Locale.TOP_TIME
                    .replace("%player%",
                            Bukkit.getOfflinePlayer(
                                    o.getName()
                                            .replaceAll(".json", "")).getName())
                    .replace("%place%", String.valueOf(i))
                    .replace("%amount%", readable)
                    .send(
                            commandSender
                    );

        }

    }
}
