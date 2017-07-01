package net.piratjsk.nocmds;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import static net.piratjsk.nocmds.Utils.colorize;

final class NoCmdsCommand implements CommandExecutor {

    private final NoCmds nocmds;
    private static final String TAG = "&f[&cNo&8Cmds&f] ";

    NoCmdsCommand(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("nocmds.admin")) {
            // reload config
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.nocmds.reloadConfig();
                sender.sendMessage(colorize(TAG + "Configuration was reloaded."));
                return true;
            }
            // list blocked commands
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(colorize(TAG + "Blocked commands:"));
                for (final String command : this.nocmds.getBlockedCommands())
                    sender.sendMessage(" " + command);
                return true;
            }
            // add blocked command
            if (args.length >= 1 && args[0].equalsIgnoreCase("block")) {
                if (args.length == 1) {
                    sender.sendMessage(colorize(TAG + "/"+cmd+" "+args[0]+" <command> [command] [...]"));
                    return true;
                }
                final StringBuilder cmds = new StringBuilder();
                for (int i = 1;i<args.length;i++) {
                    this.nocmds.blockCmd(args[i]);
                    if (i == 1) cmds.append(args[i]);
                    else cmds.append(", ").append(args[i]);
                }
                this.nocmds.saveConfig();
                sender.sendMessage(colorize(TAG + "Blocked command(s): "+cmds.toString()));
                return true;
            }
            // remove blocked command
            if (args.length >= 1 && args[0].equalsIgnoreCase("unblock")) {
                if (args.length == 1) {
                    sender.sendMessage(colorize(TAG + "/" + cmd + " " + args[0] + " <command> [command] [...]"));
                    return true;
                }
                final StringBuilder cmds = new StringBuilder();
                for (int i = 1;i<args.length;i++) {
                    this.nocmds.unblockCmd(args[i]);
                    if (i == 1) cmds.append(args[i]);
                    else cmds.append(", ").append(args[i]);
                }
                this.nocmds.saveConfig();
                sender.sendMessage("[NoCmds] Unblocked command(s): "+cmds.toString());
                return true;
            }

            sender.sendMessage(colorize(TAG + "Available commands:"));
            sender.sendMessage(colorize(" &8/&fnocmds reload - &7reloads plugin config"));
            sender.sendMessage(colorize(" &8/&fnocmds list - &7lists blocked commands"));
            sender.sendMessage(colorize(" &8/&fnocmds block &8<&fcmd&8> &7[cmd] [...] &r- &7blocks given commands(s)"));
            sender.sendMessage(colorize(" &8/&fnocmds unblock &8<&fcmd&8> &7[cmd] [...] &r- &7unblocks given commands(s)"));
            return true;
        }
        sender.sendMessage(this.nocmds.getUnknownCommandMessage());
        return true;
    }

}
