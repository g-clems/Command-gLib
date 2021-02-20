package net.feedthemadness.glib.command.executor;

import net.feedthemadness.glib.command.Main;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandExecutor;
import net.feedthemadness.glib.command.executor.ExecutorReference;

public class CommandUsageExecutor extends CommandExecutor {
	
	public CommandUsageExecutor(String id) {
		super(id);
	}
	
	@Override
	public void dispatch(CommandContext context) {
		Object[] args = new Object[] {context};
		
		for (int i = 0; i < executorReferences.length; i++) {
			ExecutorReference executor = executorReferences[i];
			
			if(!id.equals(executor.getId())) continue;
			
			if(!executor.validateType(args)) {
				Main.getTerminal().severe("Mismatch argument type during dispatch");
				//TODO proper error
				continue;
			}
			
			executor.dispatch(args);
		}
	}
}
