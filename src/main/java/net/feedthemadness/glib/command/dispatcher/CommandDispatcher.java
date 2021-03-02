package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.Main;
import net.feedthemadness.glib.command.executor.CommandUsageExecutor;
import net.feedthemadness.glib.command.executor.CommandUsageListener;
import net.feedthemadness.glib.command.executor.ExecutorReference;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class CommandDispatcher implements ICommandDispatcher {
	
	protected Command[] commands = new Command[0];
	protected CommandUsageExecutor[] usageExecutors = new CommandUsageExecutor[0];
	
	public CommandDispatcher() {
	}
	
	@Override
	public CommandDispatcher addCommand(Command command) {
		Command[] commands = Arrays.copyOf(this.commands, this.commands.length + 1);
		commands[commands.length - 1] = command;
		
		this.commands = commands;
		return this;
	}
	
	@Override
	public CommandDispatcher addUsageExecutor(ICommandExecutor executor, String id) {
		Method[] methods = executor.getClass().getDeclaredMethods();
		CommandUsageExecutor usageExecutor = new CommandUsageExecutor(id);
		
		boolean noListenerMethod = true;
		
		for(int i = 0 ; i < methods.length ; i++) {
			Method method = methods[i];
			
			if(!method.isAnnotationPresent(CommandUsageListener.class)) {
				continue;
			}
			
			CommandUsageListener annotation = method.getAnnotationsByType(CommandUsageListener.class)[0];
			
			if(!id.equals(annotation.value())) {
				continue;
			}
			
			if(noListenerMethod) noListenerMethod = false;
			
			usageExecutor.addExecutor(new ExecutorReference(executor, method, annotation.value()));
		}
		
		if(noListenerMethod) {
			Main.getTerminal().warning("No listener method");
			//TODO proper error
		}
		
		CommandUsageExecutor[] usageExecutors = Arrays.copyOf(this.usageExecutors, this.usageExecutors.length + 1);
		usageExecutors[usageExecutors.length - 1] = usageExecutor;
		
		this.usageExecutors = usageExecutors;
		return this;
	}
	
	@Override
	public void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Map<String, Object> dispatchContext) {
		boolean noDispatch = true;
		
		for(int i = 0 ; i < commands.length ; i++) {
			Command command = commands[i];
			CommandContext context = new CommandContext(dispatcher, command, parsableCommand);
			if(dispatchContext != null) context.setDispatchContext(dispatchContext);
			
			boolean dispatched = command.dispatch(context);
			if(dispatched && noDispatch) noDispatch = false;
		}
		
		if(noDispatch) {
			
			for(int i = 0 ; i < usageExecutors.length ; i++) {
				usageExecutors[i].dispatch(new CommandContext(dispatcher, null, parsableCommand));
			}
		}
	}
	
}
