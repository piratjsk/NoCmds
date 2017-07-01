package net.piratjsk.nocmds;

import org.bukkit.plugin.java.JavaPlugin;

import net.piratjsk.nocmds.listeners.TabCompleteListener;
import net.piratjsk.nocmds.listeners.PlayerCommandListener;

import static net.piratjsk.nocmds.Utils.*;

import java.lang.reflect.Field;
import java.util.Collection;

public final class NoCmds extends JavaPlugin {

    private Collection<String> blockedCommands;
    private String unknownCmdMsg;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.blockedCommands = this.getConfig().getStringList("blockedCommands");

        if (isTabCompleteEventSupported()) {
            this.getServer().getPluginManager().registerEvents(new TabCompleteListener(this), this);
        } else {
            this.getLogger().info("Looks like your server doesn't support TabCompleteEvent, therefore we can't hide blocked commands from tab completions.");
        }

        this.getServer().getPluginManager().registerEvents(new PlayerCommandListener(this), this);

        this.setupUnknownCmdMsg();

        this.getCommand("nocmds").setExecutor(new NoCmdsCommand(this));
    }

    private void setupUnknownCmdMsg() {
        if (isSpigotConfigSupported()) {
            try {
                final Field field = Class.forName("org.spigotmc.SpigotConfig").getDeclaredField("unknownCommandMessage");
                this.unknownCmdMsg = colorize((String) field.get(""));
            } catch (final NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
                this.unknownCmdMsg = colorize(this.getConfig().getString("unknownCommandMessage"));
            }
        } else {
            this.unknownCmdMsg = colorize(this.getConfig().getString("unknownCommandMessage"));
        }
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.blockedCommands = this.getConfig().getStringList("blockedCommands");
        this.setupUnknownCmdMsg();
    }

    public boolean isBlocked(final String command) {
        // strip all arguments
        final String cmd = command.split(" ")[0];
        for (final String blockedCmd : this.blockedCommands) {
            // exact match
            if (cmd.equalsIgnoreCase(blockedCmd)) return true;

            // with prefix
            if (cmd.contains(":") && cmd.split(":")[1].equalsIgnoreCase(blockedCmd)) return true;
            // without prefix
            if (cmd.equalsIgnoreCase("/"+blockedCmd)) return true;
        }
        return false;
    }

    public String getUnknownCommandMessage() {
        return this.unknownCmdMsg;
    }

    public Collection<String> getBlockedCommands() {
        return this.blockedCommands;
    }

}
