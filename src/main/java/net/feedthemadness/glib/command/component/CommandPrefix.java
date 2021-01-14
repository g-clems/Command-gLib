package net.feedthemadness.glib.command.component;

import net.feedthemadness.glib.command.dispatcher.CommandContext;

public class CommandPrefix {

	private String prefix = "";

	public CommandPrefix() {
	}

	public CommandPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public CommandPrefix setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public boolean validate(CommandContext context) {
		return prefix.equals(context.getRawArg(0).substring(0, prefix.length()));
	}

}
