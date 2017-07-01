package net.piratjsk.nocmds;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class Utils {

    public static boolean isTabCompleteEventSupported() {
        return classExist("org.bukkit.event.server.TabCompleteEvent");
    }

    public static boolean isPlayerCommandPreprocessEventSupported() {
        return classExist("org.bukkit.event.player.PlayerCommandPreprocessEvent");
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

}
