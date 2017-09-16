package net.piratjsk.nocmds;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import static net.piratjsk.nocmds.Utils.colorize;
import static net.piratjsk.nocmds.Utils.sendMsg;

final class NoCmdsCommand implements CommandExecutor {

    private final NoCmds nocmds;
    private static final String TAG = "&f[&cNo&8Cmds&f] ";

    NoCmdsCommand(final NoCmds plugin) {
        this.nocmds = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.hasPermission("nocmds.admin"))
            sendMsg(nocmds.getUnknownCmdMsgTemplate(), cmd.toString(), sender);

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload"))
                return handleReloadCommand(sender);
            if (args[0].equalsIgnoreCase("list"))
                return handleListCommand(sender);
            if (args[0].equalsIgnoreCase("block"))
                return handleBlockCommand(sender, args);
            if (args[0].equalsIgnoreCase("unblock"))
                return handleUnblockCommand(sender, args);
        }

        sender.sendMessage(colorize(TAG + "Available commands:"));
        sender.sendMessage(colorize(" &8/&fnocmds reload - &7reloads plugin config"));
        sender.sendMessage(colorize(" &8/&fnocmds list - &7lists blocked commands"));
        sender.sendMessage(colorize(" &8/&fnocmds block &8<&fcmd&8> &7[cmd] [...] &r- &7blocks given command(s)"));
        sender.sendMessage(colorize(" &8/&fnocmds unblock &8<&fcmd&8> &7[cmd] [...] &r- &7unblocks given command(s)"));
        return true;
    }

    private boolean handleReloadCommand(final CommandSender sender) {
        nocmds.reloadConfig();
        sender.sendMessage(colorize(TAG + "Configuration was reloaded."));
        return true;
    }

    private boolean handleListCommand(final CommandSender sender) {
        sender.sendMessage(colorize(TAG + "Blocked commands:"));
        for (final Rule rule : nocmds.getBlockingRules())
            sender.sendMessage(" " + rule.toString());
        return true;
    }

    private boolean handleBlockCommand(final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            sender.sendMessage(colorize(TAG + "/nocmds block <command> [command] [...]"));
            return true;
        }
        final StringBuilder cmds = new StringBuilder();
        for (int i = 1; i<args.length; i++) {
            nocmds.blockCmd(args[i]);
            if (i == 1)
                cmds.append(args[i]);
            else
                cmds.append(", ").append(args[i]);
        }
        nocmds.saveConfig();
        sender.sendMessage(colorize(TAG + "Blocked command(s): " + cmds.toString()));
        return true;
    }

    private boolean handleUnblockCommand(final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            sender.sendMessage(colorize(TAG + "/nocmds unblock <command> [command] [...]"));
            return true;
        }
        final StringBuilder cmds = new StringBuilder();
        for (int i = 1;i<args.length;i++) {
            nocmds.unblockCmd(args[i]);
            if (i == 1)
                cmds.append(args[i]);
            else
                cmds.append(", ").append(args[i]);
        }
        nocmds.saveConfig();
        sender.sendMessage("[NoCmds] Unblocked command(s): " + cmds.toString());
        return true;
    }

}
