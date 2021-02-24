package net.feedthemadness.glib.command.executor.listener;

public class CommandListenerParameter {
	
	protected final String id;
	protected final ListenerParameterType parameterType;
	protected final Class<?> type;
	
	public CommandListenerParameter(String id, ListenerParameterType parameterType, Class<?> type) {
		this.id = id;
		this.parameterType = parameterType;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public ListenerParameterType getParameterType() {
		return parameterType;
	}
	
	public Class<?> getType() {
		return type;
	}
	
}
