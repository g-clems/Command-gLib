package net.feedthemadness.glib.command.executor;

import net.feedthemadness.glib.command.Main;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.listener.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class ExecutorReference {
	
	private final String id;
	
	private final ICommandExecutor executor;
	private final Method method;
	
	protected final CommandListenerParameter[] parameters;
	
	public ExecutorReference(ICommandExecutor executor, Method method, String id) {
		this.id = id;
		
		this.executor = executor;
		this.method = method;
		
		this.parameters = new CommandListenerParameter[method.getParameterCount()];
		
		for(int i = 0 ; i < method.getParameterCount() ; i++) {
			Parameter parameter = method.getParameters()[i];
			
			String parameterId = "";
			ListenerParameterType parameterType = ListenerParameterType.NONE;
			Class<?> type = parameter.getType();
			
			if(parameter.isAnnotationPresent(CmdContext.class)) {
				if(parameter.getType().isAssignableFrom(CommandContext.class)) {
					parameterType = ListenerParameterType.CONTEXT;
				}
			} else if(parameter.isAnnotationPresent(CmdDispatcher.class)) {
				if(parameter.getType().isAssignableFrom(ICommandDispatcher.class)) {
					parameterType = ListenerParameterType.DISPATCHER;
				}
			} else if(parameter.isAnnotationPresent(CmdArg.class)) {
				CmdArg annotation = parameter.getAnnotationsByType(CmdArg.class)[0];
				
				parameterId = annotation.value();
				parameterType = ListenerParameterType.ARGUMENT_CONTEXT;
			} else if(parameter.isAnnotationPresent(CmdDispatch.class)) {
				CmdDispatch annotation = parameter.getAnnotationsByType(CmdDispatch.class)[0];
				
				parameterId = annotation.value();
				parameterType = ListenerParameterType.DISPATCH_CONTEXT;
			} else if(parameter.isAnnotationPresent(CmdOption.class)) {
				CmdOption annotation = parameter.getAnnotationsByType(CmdOption.class)[0];
				
				parameterId = annotation.value();
				parameterType = ListenerParameterType.OPTION_CONTEXT;
			} else {
				Main.getTerminal().severe("Missing argument during register. Will always be null");
			}
			
			if(type == boolean.class) {
				type = Boolean.class;
			} else if(type == byte.class) {
				type = Byte.class;
			} else if(type == char.class) {
				type = Character.class;
			} else if(type == short.class) {
				type = Short.class;
			} else if(type == int.class) {
				type = Integer.class;
			} else if(type == long.class) {
				type = Long.class;
			} else if(type == float.class) {
				type = Float.class;
			} else if(type == double.class) {
				type = Double.class;
			}
			
			this.parameters[i] = new CommandListenerParameter(parameterId, parameterType, type);
		}
		
	}
	
	public String getId() {
		return id;
	}
	
	public void dispatch(Object[] parameters) {
		try {
			method.invoke(executor, parameters);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public Object[] buildParameters(CommandContext context) {
		Object[] parameters = new Object[this.parameters.length];
		Map<String, Object> dispatchContext = context.getDispatchContext();
		Map<String, Object> argsContext = context.getArgumentContext();
		
		for(int i = 0 ; i < parameters.length ; i++) {
			CommandListenerParameter parameter = this.parameters[i];
			
			if(parameter.getParameterType().equals(ListenerParameterType.NONE)) {
				parameters[i] = null;
			} else if(parameter.getParameterType().equals(ListenerParameterType.CONTEXT)) {
				parameters[i] = context;
			} else if(parameter.getParameterType().equals(ListenerParameterType.DISPATCHER)) {
				parameters[i] = context.getDispatcher();
			} else if(parameter.getParameterType().equals(ListenerParameterType.ARGUMENT_CONTEXT)) {
				if(argsContext.containsKey(parameter.getId())) {
					Object commandParameter = argsContext.get(parameter.getId());
					if(parameter.getType().isAssignableFrom(commandParameter.getClass())) {
						parameters[i] = commandParameter;
					} else {
						Main.getTerminal().error("Mismatch argument " + parameter.getId() + " during dispatch");
					}
				} else {
					Main.getTerminal().error("Missing argument " + parameter.getId() + " during dispatch");
				}
			} else if(parameter.getParameterType().equals(ListenerParameterType.DISPATCH_CONTEXT)) {
				if(dispatchContext.containsKey(parameter.getId())) {
					Object dispatchParameter = dispatchContext.get(parameter.getId());
					if(parameter.getType().isAssignableFrom(dispatchParameter.getClass())) {
						parameters[i] = dispatchParameter;
					} else {
						Main.getTerminal().error("Mismatch argument " + parameter.getId() + " during dispatch");
					}
				} else {
					Main.getTerminal().error("Missing argument " + parameter.getId() + " during dispatch");
				}
			} else if(parameter.getParameterType().equals(ListenerParameterType.OPTION_CONTEXT)) {
				if(argsContext.containsKey(parameter.getId())) {
					Object commandParameter = argsContext.get(parameter.getId());
					if(parameter.getType().isAssignableFrom(commandParameter.getClass())) {
						parameters[i] = commandParameter;
					} else {
						Main.getTerminal().error("Mismatch argument " + parameter.getId() + " during dispatch");
					}
				} else {
					Main.getTerminal().error("Missing argument " + parameter.getId() + " during dispatch");
				}
			}
		}
		return parameters;
	}
	
}
