package net.feedthemadness.glib.command.executor;

import net.feedthemadness.glib.command.dispatcher.CommandContext;

public class CommandUsageExecutor extends CommandExecutor {
	
	public CommandUsageExecutor(String id) {
		super(id);
	}
	
	@Override
	public void dispatch(CommandContext context) {
		Object[] parameters = new Object[]{context};
		
		for(int i = 0 ; i < executorReferences.length ; i++) {
			ExecutorReference executor = executorReferences[i];
			
			if(!id.equals(executor.getId())) continue;
			
			executor.dispatch(parameters);
		}
	}
	
}
