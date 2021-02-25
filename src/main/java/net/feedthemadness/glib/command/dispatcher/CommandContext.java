package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.executor.CommandUsageExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandContext {
	
	private final ICommandDispatcher dispatcher;
	private final Command command;
	private final String[] parsableArguments;
	
	private Map<String, Object> dispatchContext = new HashMap<>();
	private Map<String, Object> argumentContext = new HashMap<>();
	
	private CommandUsageExecutor[] usage = new CommandUsageExecutor[0];
	
	public CommandContext(ICommandDispatcher dispatcher, Command command, String parsableCommand) {
		this.dispatcher = dispatcher;
		this.command = command;
		this.parsableArguments = parsableCommand.split(" ");
	}
	
	public ICommandDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public int parsableArgumentsSize() {
		return parsableArguments.length;
	}
	
	public String getParsableArgument(int index) {
		return parsableArguments[index];
	}
	
	public Map<String, Object> getDispatchContext() {
		return dispatchContext;
	}
	
	public void setDispatchContext(Map<String, Object> dispatchContext) {
		this.dispatchContext = dispatchContext;
	}
	
	public void putDispatch(String key, Object value) {
		dispatchContext.put(key, value);
	}
	
	public Map<String, Object> getArgumentContext() {
		return argumentContext;
	}
	
	public void setArgumentContext(Map<String, Object> argumentContext) {
		this.argumentContext = argumentContext;
	}
	
	public void putArgument(String key, Object value) {
		argumentContext.put(key, value);
	}
	
	public CommandUsageExecutor[] getUsage() {
		return Arrays.copyOf(usage, usage.length);
	}
	
	public void setUsage(CommandUsageExecutor[] usage) {
		this.usage = usage;
	}
	
}
