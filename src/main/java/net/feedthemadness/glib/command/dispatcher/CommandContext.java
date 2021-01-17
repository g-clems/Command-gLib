package net.feedthemadness.glib.command.dispatcher;

import java.util.Arrays;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.sub.ICommandElementType;

public class CommandContext implements Cloneable {
	
	private final ICommandDispatcher dispatcher;
	private final Command command;
	private final Object[] dispatchContext;
	private final String[] parsableArgs;
	private ICommandElementType[] args;
	
	public CommandContext(ICommandDispatcher dispatcher, Command command, Object[] dispatchContext, String parsableCommand) {
		this.dispatcher = dispatcher;
		this.command = command;
		this.dispatchContext = dispatchContext;
		this.parsableArgs = parsableCommand.split(" ");
		this.args = new ICommandElementType[parsableArgs.length - 1];
	}
	
	protected CommandContext(CommandContext context) {
		this.dispatcher = context.dispatcher;
		this.command = context.command;
		this.dispatchContext = Arrays.copyOf(context.dispatchContext, context.dispatchContext.length);
		this.parsableArgs = Arrays.copyOf(context.parsableArgs, context.parsableArgs.length);
		this.args = Arrays.copyOf(context.args, context.args.length);
	}
	
	public ICommandDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public Object[] getDispatchContext() {
		return dispatchContext;
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
	
	@Override
	public CommandContext clone() {
		return new CommandContext(this);
	}
	
}
