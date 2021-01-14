package net.feedthemadness.glib.command.executor;

import java.util.Arrays;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;

public class CommandExecutor {
	
	private final String id;
	private ExecutorReference[] executorReferences = new ExecutorReference[0];
	
	public CommandExecutor(String id) {
		this.id = id;
	}
	
	public CommandExecutor addExecutor(ExecutorReference executorReference) {
		ExecutorReference[] executors = Arrays.copyOf(this.executorReferences, this.executorReferences.length + 1);
		executors[executors.length - 1] = executorReference;

		this.executorReferences = executors;
		return this;
	}
	
	public void dispatch(ICommandDispatcher dispatcher, CommandContext context, Command command, int depth) {
		
		Object[] args = new Object[depth + 3];
		args[0] = dispatcher;
		args[1] = context;
		args[2] = command;
		for(int j = 0; j < depth; j++) {
			args[j + 3] = context.getArgType(j).parse(context.getRawArg(j + 1));
		}
		
		for(int i = 0; i < executorReferences.length; i++) {
			ExecutorReference executor = executorReferences[i];
			
			if(!id.equals(executor.getId())) continue;
			
			if(!executor.validateType(args)) {
				//TODO proper error
				continue;
			}
			
			executor.dispatch(dispatcher, context, command, depth, args);
		}
	}
	
}
