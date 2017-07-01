package net.piratjsk.nocmds;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

final class NoCmdsCommand implements CommandExecutor {

    private final NoCmds nocmds;

    NoCmdsCommand(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("nocmds.admin")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.nocmds.reloadConfig();
                sender.sendMessage("[NoCmds] Configuration was reloaded.");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                sender.sendMessage("[NoCmds] blockedCommands:");
                for (final String command : this.nocmds.getBlockedCommands())
                    sender.sendMessage(" "+command);
                return true;
            }
        }
        sender.sendMessage("[NoCmds] :)");
        return true;
    }

}
