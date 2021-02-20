package net.feedthemadness.glib.command.privateroom;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.CommandUsageListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class CommandBotListener implements ICommandExecutor {
	
	@CommandUsageListener("help")
	public void help(CommandContext context) {
		System.out.println("default help");
	}
	
	@CommandUsageListener("default")
	public void usage(CommandContext context) {
		System.out.println("default usage");
	}
	
	@CommandListener("config name")
	public void configName(CommandContext context, String arg1, String name) {
		System.out.println(name);
	}
	
	@CommandUsageListener("config whitelist usage")
	public void configWhitelistHelp(CommandContext context) {
		System.out.println("whitelist usage");
	}
	
	@CommandListener("config whitelist add")
	public void configWhitelistAdd(CommandContext context, String arg1, String arg2, String id) {
		System.out.println("whitelist add " + id);
	}
	
	@CommandListener("config whitelist remove")
	public void configWhitelistRemove(CommandContext context, String arg1, String arg2, String id) {
		System.out.println("whitelist remove " + id);
	}
	
}
