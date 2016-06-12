package net.piratjsk.nocmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

import java.util.ArrayList;
import java.util.List;

public final class NoCmds extends JavaPlugin implements Listener {

    private List<String> blockedCommands;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.blockedCommands = this.getConfig().getStringList("blockedCommands");

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("nocmds").setExecutor(this);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("nocmds.admin")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.reloadConfig();
                this.blockedCommands = this.getConfig().getStringList("blockedCommands");
                sender.sendMessage("[NoCmds] Configuration was reloaded.");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                sender.sendMessage("[NoCmds] blockedCommands:");
                for (String command : blockedCommands)
                    sender.sendMessage(" "+command);
                return true;
            }
        }
        sender.sendMessage("[NoCmds] :)");
        return true;
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("nocmds.bypass")) return;
        final String cmd = event.getMessage().split(" ")[0];
        boolean blocked = false;
        for (final String blockedCmd : this.blockedCommands) {
            if (blockedCmd.startsWith("/") && cmd.equalsIgnoreCase(blockedCmd)) {
                blocked = true;
                break;
            } else {
                if (cmd.contains(":") && cmd.split(":")[1].equalsIgnoreCase(blockedCmd)) {
                    blocked = true;
                    break;
                } else if (cmd.equalsIgnoreCase("/"+blockedCmd)) {
                    blocked = true;
                    break;
                }
            }
        }
        if (blocked) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(SpigotConfig.unknownCommandMessage);
        }
    }

    @EventHandler
    public void onCommandTabComplete(final TabCompleteEvent event) {
        if (event.getSender().hasPermission("nocmds.bypass")) return;
        final ArrayList<String> completions = new ArrayList<String>(event.getCompletions());
        for (final String cmplt : (ArrayList<String>)completions.clone()) {
            for (final String blockedCmd : this.blockedCommands) {
                if (blockedCmd.startsWith("/")) {
                    if (cmplt.equalsIgnoreCase(blockedCmd)) completions.remove(cmplt);
                } else {
                    if (cmplt.contains(":") && cmplt.split(":")[1].equalsIgnoreCase(blockedCmd))
                        completions.remove(cmplt);
                    else if (cmplt.equalsIgnoreCase("/" + blockedCmd))
                        completions.remove(cmplt);
                }
            }
        }
        event.setCompletions(completions);
    }

}
