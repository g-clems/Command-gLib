package net.feedthemadness.glib.command.dispatcher;

import java.util.List;

import net.feedthemadness.glib.command.Command;

public class CommandRegister {
	
	public static void registerCommand(Command command, ICommandDispatcher...commandExecutors) {
		
		for (int i = 0 ; i < commandExecutors.length ; i++) {
			CommandDispatcher executor = commandExecutors[i].getCommandDispatcher();
			executor.addCommand(command);
		}
	}
	
	public static void registerCommands(List<Command> commands, ICommandDispatcher...commandExecutors) {
		
		commands.forEach((command) -> {
			registerCommand(command, commandExecutors);
		});
	}
	
}
