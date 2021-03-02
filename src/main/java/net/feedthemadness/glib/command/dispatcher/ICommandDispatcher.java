package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

import java.util.Map;

public interface ICommandDispatcher {
	
	ICommandDispatcher addCommand(Command command);
	
	CommandDispatcher addUsageExecutor(ICommandExecutor executor, String id);
	
	void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Map<String, Object> dispatchContext);
	
}
