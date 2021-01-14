package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.sub.ICommandElementType;

public class CommandContext {
	
	private final String[] parsableArgs;
	private ICommandElementType[] args;
	
	public CommandContext(String parsableCommand) {
		this.parsableArgs = parsableCommand.split(" ");
		this.args = new ICommandElementType[parsableArgs.length - 1];
	}
	
	public int size() {
		return parsableArgs.length;
	}
	
	public String getRawArg(int index) {
		return parsableArgs[index];
	}
	
	public ICommandElementType getArgType(int index) {
		return args[index];
	}
	
	public void setArgType(int index, ICommandElementType arg) {
		args[index] = arg;
	}
	
}
