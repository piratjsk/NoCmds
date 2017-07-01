package net.piratjsk.nocmds.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.piratjsk.nocmds.NoCmds;
import static net.piratjsk.nocmds.Utils.isSpigotConfigSupported;
import static net.piratjsk.nocmds.Utils.commandExists;
import static net.piratjsk.nocmds.Utils.sendMsg;

public final class PlayerCommandListener implements Listener {

    private final NoCmds nocmds;

    public PlayerCommandListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("nocmds.bypass")) return;

        final String cmd = event.getMessage().split(" ")[0];

        // handle blocked commands
        if (this.nocmds.isBlocked(event.getMessage())) {
            event.setCancelled(true);
            sendMsg(this.nocmds.getUnknownCommandMessage(), cmd, event.getPlayer());
            return;
        }

        // handle unknown commands
        if (!commandExists(event.getMessage())) {
            if (isSpigotConfigSupported() && !this.nocmds.getConfig().getBoolean("ignoreSpigotConfig"))
                return;
            event.setCancelled(true);
            sendMsg(this.nocmds.getUnknownCommandMessage(), cmd, event.getPlayer());
        }

    }

}
