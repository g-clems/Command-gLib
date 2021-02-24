package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.command.ISubCommandType;
import net.feedthemadness.glib.command.sub.command.SubCommandTypeLabel;
import net.feedthemadness.glib.command.sub.command.SubCommandTypeString;

public class SubCommand extends ASubCommandElement {
	
	protected ISubCommandType type = new SubCommandTypeString();
	
	public SubCommand(String name) {
		super(name);
	}
	
	public SubCommand(String name, String label) {
		super(name);
		this.type = new SubCommandTypeLabel(label);
	}
	
	public SubCommand(String name, String label, String... aliases) {
		super(name);
		this.type = new SubCommandTypeLabel(label, aliases);
	}
	
	@Override
	public SubCommand addSubElement(ACommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public SubCommand addExecutor(ICommandExecutor executor, String id) {
		super.addExecutor(executor, id);
		return this;
	}
	
	public ISubCommandType getType() {
		return type;
	}
	
	public SubCommand setType(ISubCommandType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		
		if(!type.validate(context.getParsableArgument(depth))) return false;
		
		context.putArgument(name, type.parse(context.getParsableArgument(depth)));
		
		return true;
	}
	
}
