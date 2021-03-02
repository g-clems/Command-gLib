package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.*;
import net.feedthemadness.glib.command.sub.ASubCommandElement;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class ACommandElement {
	
	protected ASubCommandElement[] subElements = new ASubCommandElement[0];
	
	protected CommandUsageExecutor[] usageExecutors = new CommandUsageExecutor[0];
	protected CommandExecutor[] commandExecutors = new CommandExecutor[0];
	
	public ACommandElement addSubElement(ASubCommandElement subElement) {
		ASubCommandElement[] subCommands = Arrays.copyOf(this.subElements, this.subElements.length + 1);
		subCommands[subCommands.length - 1] = subElement;
		
		this.subElements = subCommands;
		return this;
	}
	
	public ACommandElement addExecutor(ICommandExecutor executor, String id) {
		Method[] methods = executor.getClass().getDeclaredMethods();
		CommandExecutor commandExecutor = new CommandExecutor(id);
		
		boolean noListenerMethod = true;
		
		for(int i = 0 ; i < methods.length ; i++) {
			Method method = methods[i];
			
			if(!method.isAnnotationPresent(CommandListener.class)) {
				continue;
			}
			
			CommandListener annotation = method.getAnnotationsByType(CommandListener.class)[0];
			
			if(!id.equals(annotation.value())) {
				continue;
			}
			
			//TODO proper error
			if(noListenerMethod) noListenerMethod = false;
			
			commandExecutor.addExecutor(new ExecutorReference(executor, method, annotation.value()));
		}
		
		CommandExecutor[] commandExecutors = Arrays.copyOf(this.commandExecutors, this.commandExecutors.length + 1);
		commandExecutors[commandExecutors.length - 1] = commandExecutor;
		
		this.commandExecutors = commandExecutors;
		return this;
	}
	
	public ACommandElement addUsageExecutor(ICommandExecutor executor, String id) {
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
	
	protected boolean dispatch(CommandContext context, int depth) {
		boolean noExecution = commandExecutors.length == 0;
		
		for(int i = 0 ; i < commandExecutors.length ; i++) {
			CommandExecutor commandExecutor = commandExecutors[i];
			
			commandExecutor.dispatch(context);
		}
		
		if(usageExecutors.length > 0) context.setUsage(usageExecutors);
		
		depth++;
		if(depth >= context.parsableArgumentsSize()) {
			if(noExecution) {
				usageDispatch(context);
			}
			return true;
		}
		boolean noDispatch = true;
		
		for(int i = 0 ; i < subElements.length ; i++) {
			ACommandElement subElement = subElements[i];
			
			if(!subElement.checkDispatch(context, depth)) {
				continue;
			}
			
			if(noDispatch) noDispatch = false;
			
			if(depth < context.parsableArgumentsSize()) subElement.dispatch(context, depth);
		}
		
		if(noDispatch) {
			usageDispatch(context);
		}
		
		return true;
	}
	
	public abstract boolean checkDispatch(CommandContext context, int depth);
	
	protected void usageDispatch(CommandContext context) {
		
		CommandUsageExecutor[] usageExecutors = context.getUsage();
		
		for(int i = 0 ; i < usageExecutors.length ; i++) {
			CommandUsageExecutor usageExecutor = usageExecutors[i];
			
			usageExecutor.dispatch(context);
		}
	}
	
}
