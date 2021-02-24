package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;

import java.util.List;

public class CommandRegister {
	
	public static void registerCommand(Command command, ICommandDispatcher... commandDispatchers) {
		
		for(int i = 0 ; i < commandDispatchers.length ; i++) {
			ICommandDispatcher dispatcher = commandDispatchers[i];
			dispatcher.addCommand(command);
		}
	}
	
	public static void registerCommands(List<Command> commands, ICommandDispatcher... commandDispatchers) {
		
		commands.forEach((command) -> registerCommand(command, commandDispatchers));
	}
	
}
