package net.piratjsk.nocmds.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;

import net.piratjsk.nocmds.NoCmds;

import java.util.ArrayList;

public final class TabCompleteListener implements Listener {

    private final NoCmds nocmds;

    public TabCompleteListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onCommandTabComplete(final TabCompleteEvent event) {
        if (event.getSender().hasPermission("nocmds.bypass")) return;

        final ArrayList<String> completions = new ArrayList<String>(event.getCompletions());
        for (final String cmplt : completions) {
            if (this.nocmds.isBlocked(cmplt)) {
                completions.remove(cmplt);
            }
        }
        event.setCompletions(completions);

    }

}
