package net.piratjsk.nocmds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.piratjsk.nocmds.NoCmds;
import static net.piratjsk.nocmds.Utils.isSpigotConfigSupported;

public final class PlayerCommandListener implements Listener {

    private final NoCmds nocmds;

    public PlayerCommandListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("nocmds.bypass")) return;

        // handle blocked commands
        if (this.nocmds.isBlocked(event.getMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.nocmds.getUnknownCommandMessage());
            return;
        }

        // handle unknown commands
        if (!commandExists(event.getMessage())) {
            if (isSpigotConfigSupported())
                return;
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.nocmds.getUnknownCommandMessage());
        }

    }

    private boolean commandExists(final String command) {
        final String cmd = command.split(" ")[0];
        return Bukkit.getHelpMap().getHelpTopic(cmd) != null;
    }

}
