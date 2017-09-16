package net.piratjsk.nocmds.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;

import net.piratjsk.nocmds.NoCmds;

import java.util.List;
import java.util.stream.Collectors;

public final class TabCompleteListener implements Listener {

    private final NoCmds nocmds;

    public TabCompleteListener(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @EventHandler
    public void onCommandTabComplete(final TabCompleteEvent event) {
        if (event.getSender().hasPermission("nocmds.bypass"))
            return;
        event.setCompletions(filterCompletions(event.getCompletions()));
    }

    private List<String> filterCompletions(final List<String> completions) {
        return completions
                .stream()
                .filter(nocmds::isBlocked)
                .collect(Collectors.toList());
    }

}
