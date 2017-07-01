package net.piratjsk.nocmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static boolean isTabCompleteEventSupported() {
        return classExist("org.bukkit.event.server.TabCompleteEvent");
    }

    public static boolean isSpigotConfigSupported() {
        return classExist("org.spigotmc.SpigotConfig");
    }

    private static boolean classExist(final String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    public static String colorize(final String uncoloredString) {
        return ChatColor.translateAlternateColorCodes('&', uncoloredString);
    }

    public static boolean commandExists(final String command) {
        final String cmd = command.split(" ")[0];
        return Bukkit.getHelpMap().getHelpTopic(cmd) != null;
    }

    public static void sendMsg(String message, final String command, final CommandSender sender) {
        message = message
                .replaceAll("(%name%|%player%)", sender.getName())
                .replaceAll("%command%", command);
        if (sender instanceof Player)
            message = message.replaceAll("%displayname%", ((Player) sender).getDisplayName());
        else
            message = message.replaceAll("%displayname%", sender.getName());

        sender.sendMessage(message);
    }

}
