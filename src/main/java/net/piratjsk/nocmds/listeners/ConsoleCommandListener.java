package net.piratjsk.nocmds.listeners;

import net.piratjsk.nocmds.NoCmds;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerCommandEvent;

import static net.piratjsk.nocmds.Utils.isSpigotConfigSupported;
import static net.piratjsk.nocmds.Utils.commandExists;
import static net.piratjsk.nocmds.Utils.sendMsg;

public class ConsoleCommandListener implements Listener {

    private final NoCmds nocmds;

    public ConsoleCommandListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onConsoleCommand(final ServerCommandEvent event) {
        if (isSpigotConfigSupported() && !this.nocmds.getConfig().getBoolean("ignoreSpigotConfig"))
            return;

        if (!commandExists(event.getCommand())) {
            event.setCancelled(true);
            sendMsg(this.nocmds.getUnknownCommandMessage(), event.getCommand().split(" ")[0], event.getSender());
        }
    }

}
