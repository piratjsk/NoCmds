package net.piratjsk.nocmds;

public class Rule {

    private final boolean strict;
    private final String rule;

    public Rule(final String rule) {
        this.strict = rule.startsWith("/");
        this.rule = isStrict() ? rule.substring(1) : rule;
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isMatching(final String command) {
        if (command.equalsIgnoreCase(this.rule))
            return true;
        else if (isStrict())
            return false;

        final String cmdWithoutPrefix = stripPrefix(command);
        return cmdWithoutPrefix.equalsIgnoreCase(this.rule);

    }

    private String stripPrefix(String command) {
        if (command.contains(":"))
            return command.split(":")[1];
        return command;
    }

    public String toString() {
        if (isStrict())
            return "/".concat(rule);
        return rule;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Rule))
            return false;
        final Rule otherRule = (Rule) obj;
        return this.equals(otherRule);
    }

    public boolean equals(final Rule otherRule) {
        return this.toString().equalsIgnoreCase(otherRule.toString());
    }
}
