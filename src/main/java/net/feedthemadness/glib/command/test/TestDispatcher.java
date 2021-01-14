package net.feedthemadness.glib.command.test;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.CommandDispatcher;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.test.utils.TerminalWrapper;

public class TestDispatcher implements ICommandDispatcher, ICommandExecutor {
	
	private CommandDispatcher commandDispatcher = new CommandDispatcher();
	
	private volatile boolean stop;
	
	public void startListener() {
		TerminalWrapper tw = new TerminalWrapper();
		String command = "";
		
		while(!stop) {
			command = tw.requestString("");
			commandDispatcher.dispatch(command, this);
		}
	}
	
	@Override
	public CommandDispatcher getCommandDispatcher() {
		return commandDispatcher;
	}
	
	@CommandListener("stop")
	public void stop(ICommandDispatcher dispatcher, CommandContext context, Command command) {
		stop = true;
	}
	
}
