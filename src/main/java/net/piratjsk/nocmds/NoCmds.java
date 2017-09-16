package net.piratjsk.nocmds;

import net.piratjsk.nocmds.listeners.ConsoleCommandListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.piratjsk.nocmds.listeners.TabCompleteListener;
import net.piratjsk.nocmds.listeners.PlayerCommandListener;

import static net.piratjsk.nocmds.Utils.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public final class NoCmds extends JavaPlugin {

    private final Collection<Rule> blockingRules = new ArrayList<>();
    private String unknownCmdMsgTemplate;

    @Override
    public void onEnable() {
        setupConfig();
        registerEvents();
        setupCommand();
    }

    private void setupConfig() {
        saveDefaultConfig();
        setupBlockingRules();
        setupUnknownCmdMsgTemplate();
    }

    private void setupBlockingRules() {
        for (final String rule : this.getConfig().getStringList("blockedCommands"))
            blockingRules.add(new Rule(rule));
    }

    private void setupUnknownCmdMsgTemplate() {
        if (isSpigotConfigSupported() && !shouldIgnoreSpigotConfig()) {
            try {
                final Field field = Class.forName("org.spigotmc.SpigotConfig").getDeclaredField("unknownCommandMessage");
                unknownCmdMsgTemplate = colorize((String) field.get(""));
            } catch (final NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
                getLogger().warning("Something went wrong when trying to get unknown command message from spigot config, message from NoCmds config will be used instead.");
                unknownCmdMsgTemplate = colorize(getConfig().getString("unknownCommandMessage"));
            }
        } else {
            unknownCmdMsgTemplate = colorize(getConfig().getString("unknownCommandMessage"));
        }
    }

    public boolean shouldIgnoreSpigotConfig() {
        return getConfig().getBoolean("ignoreSpigotConfig");
    }

    private void registerEvents() {
        registerEvent(new PlayerCommandListener(this));

        if (isTabCompleteEventSupported())
            registerEvent(new TabCompleteListener(this));
        else
            getLogger().info("Looks like your server doesn't support TabCompleteEvent, therefore we can't hide blocked commands from tab completions.");

        registerEvent(new ConsoleCommandListener(this));
    }

    private void registerEvent(final Listener eventListener) {
        getServer().getPluginManager().registerEvents(eventListener, this);
    }

    private void setupCommand() {
        getCommand("nocmds").setExecutor(new NoCmdsCommand(this));
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        setupBlockingRules();
        setupUnknownCmdMsgTemplate();
    }

    @Override
    public void saveConfig() {
        final String[] rules = blockingRules.stream().map(Rule::toString).toArray(String[]::new);
        getConfig().set("blockedCommands", rules);
        super.saveConfig();
    }

    public boolean isBlocked(final String command) {
        // strip all arguments
        final String cmd = command.split(" ")[0];
        return isMatchingAnyRule(cmd);
    }

    private boolean isMatchingAnyRule(final String command) {
        for (final Rule rule : blockingRules) {
            if (rule.isMatching(command))
                return true;
        }
        return false;

    }

    public String getUnknownCmdMsgTemplate() {
        return unknownCmdMsgTemplate;
    }

    public Collection<Rule> getBlockingRules() {
        return blockingRules;
    }

    public void blockCmd(final String command) {
        addRule(new Rule(command));
    }

    public void addRule(final Rule rule) {
        blockingRules.add(rule);
    }

    public void unblockCmd(final String command) {
        removeRule(new Rule(command));
    }

    public void removeRule(final Rule rule) {
        blockingRules.remove(rule);
    }

}
