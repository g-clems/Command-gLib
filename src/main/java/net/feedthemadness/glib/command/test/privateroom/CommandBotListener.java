package net.feedthemadness.glib.command.test.privateroom;

import net.feedthemadness.glib.command.component.CommandLabel;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class CommandBotListener implements ICommandExecutor {
	
	@CommandListener("config name")
	public void configName(CommandContext context, CommandLabel label, String arg1, String name) {
		System.out.println(label.getLabel());
		System.out.println(name);
	}
	
}
