package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public interface ICommandDispatcher {
	
	ICommandDispatcher addCommand(Command command);
	
	CommandDispatcher addUsageExecutor(ICommandExecutor executor, String id);
	
	void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Object... dispatchContext);
	
}
