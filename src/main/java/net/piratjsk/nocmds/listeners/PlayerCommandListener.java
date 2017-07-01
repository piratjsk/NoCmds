package net.piratjsk.nocmds.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.piratjsk.nocmds.NoCmds;

public final class PlayerCommandListener implements Listener {

    private final NoCmds nocmds;

    public PlayerCommandListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("nocmds.bypass")) return;

        if (this.nocmds.isBlocked(event.getMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.nocmds.getUnknownCommandMessage());
        }

    }

}
