package net.feedthemadness.glib.command;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.CommandExecutor;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ExecutorReference;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public abstract class ACommandElement {
	
	protected ACommandElement[] subElements = new ACommandElement[0];
	
	protected CommandExecutor[] commandExecutors = new CommandExecutor[0];
	
	public ACommandElement addSubElement(ACommandElement subElement) {
		ACommandElement[] subCommands = Arrays.copyOf(this.subElements, this.subElements.length + 1);
		subCommands[subCommands.length - 1] = subElement;

		this.subElements = subCommands;
		return this;
	}
	
	public ACommandElement addExecutor(ICommandExecutor executor, String id) {
		Method[] methods = executor.getClass().getDeclaredMethods();
		CommandExecutor commandExecutor = new CommandExecutor(id);
		
		boolean noListenerMethod = true;
		
		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			
			if(!method.isAnnotationPresent(CommandListener.class)) {
				continue;
			}
			
			CommandListener annotation = method.getAnnotationsByType(CommandListener.class)[0];
			
			if(!id.equals(annotation.value())) {
				continue;
			}
			
			if(noListenerMethod) noListenerMethod = false;
			
			commandExecutor.addExecutor(new ExecutorReference(executor, method, annotation.value()));
		}

		CommandExecutor[] commandExecutors = Arrays.copyOf(this.commandExecutors, this.commandExecutors.length + 1);
		commandExecutors[commandExecutors.length - 1] = commandExecutor;
		
		this.commandExecutors = commandExecutors;
		return this;
	}
	
	protected void dispatch(ICommandDispatcher dispatcher, CommandContext context, Command command, int depth) {
		
		for(int j = 0 ; j < commandExecutors.length ; j++) {
			CommandExecutor commandExecutor = commandExecutors[j];
			
			commandExecutor.dispatch(dispatcher, context, command, depth);
		}
		
		depth++;
		if(depth >= context.size()) return;
		
		for(int i = 0 ; i < subElements.length ; i++) {
			ACommandElement subElement = subElements[i];
			
			if(!subElement.checkDispatch(dispatcher, context, depth)) continue;
			
			subElement.dispatch(dispatcher, context, command, depth);
		}
	}
	
	public abstract boolean checkDispatch(ICommandDispatcher dispatcher, CommandContext context, int depth);
	
}
