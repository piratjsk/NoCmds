package net.piratjsk.nocmds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    private static boolean commandExists(final String command) {
        final String cmd = command.split(" ")[0];
        return Bukkit.getHelpMap().getHelpTopic(cmd) != null;
    }

    private static void sendMsg(final String message, final String command, final Player player) {
        player.sendMessage(message
                .replaceAll("(%name%|%player%)",player.getName())
                .replaceAll("%displayname%",player.getDisplayName())
                .replaceAll("%command%",command)
        );
    }

}
