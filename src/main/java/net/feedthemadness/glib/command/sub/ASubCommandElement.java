package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public abstract class ASubCommandElement extends ACommandElement {
	
	protected final String name;
	
	public ASubCommandElement(String name) {
		this.name = name;
	}
	
	@Override
	public ASubCommandElement addSubElement(ASubCommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public ASubCommandElement addExecutor(ICommandExecutor executor, String id) {
		super.addExecutor(executor, id);
		return this;
	}
	
	@Override
	public ASubCommandElement addUsageExecutor(ICommandExecutor executor, String id) {
		super.addUsageExecutor(executor, id);
		return this;
	}
	
}
