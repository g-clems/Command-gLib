package net.feedthemadness.glib.command.executor;

import net.feedthemadness.glib.command.dispatcher.CommandContext;

import java.util.Arrays;

public class CommandExecutor {
	
	protected final String id;
	protected ExecutorReference[] executorReferences = new ExecutorReference[0];
	
	public CommandExecutor(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public CommandExecutor addExecutor(ExecutorReference executorReference) {
		ExecutorReference[] executors = Arrays.copyOf(this.executorReferences, this.executorReferences.length + 1);
		executors[executors.length - 1] = executorReference;
		
		this.executorReferences = executors;
		return this;
	}
	
	public void dispatch(CommandContext context) {
		
		for(int i = 0 ; i < executorReferences.length ; i++) {
			ExecutorReference executor = executorReferences[i];
			
			if(!id.equals(executor.getId())) continue;
			
			executor.dispatch(executor.buildParameters(context));
		}
	}
	
}
