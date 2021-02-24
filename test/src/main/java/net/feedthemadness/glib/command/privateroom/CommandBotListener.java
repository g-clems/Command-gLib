package net.feedthemadness.glib.command.privateroom;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.CommandUsageListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.executor.listener.CmdArg;
import net.feedthemadness.glib.command.executor.listener.CmdContext;

public class CommandBotListener implements ICommandExecutor {
	
	@CommandUsageListener("help")
	public void help(@CmdContext CommandContext context) {
		System.out.println("default help");
	}
	
	@CommandUsageListener("default")
	public void usage(@CmdContext CommandContext context) {
		System.out.println("default usage");
	}
	
	@CommandListener("config name")
	public void configName(@CmdContext CommandContext context, @CmdArg("name") String name) {
		System.out.println("rename channel to " + name);
	}
	
	@CommandUsageListener("config whitelist usage")
	public void configWhitelistHelp(@CmdContext CommandContext context) {
		System.out.println("whitelist usage");
	}
	
	@CommandListener("config whitelist add")
	public void configWhitelistAdd(@CmdContext CommandContext context, @CmdArg("user") String user) {
		System.out.println("whitelist add " + user);
	}
	
	@CommandListener("config whitelist remove")
	public void configWhitelistRemove(@CmdContext CommandContext context, @CmdArg("user") String user) {
		System.out.println("whitelist remove " + user);
	}
	
}
