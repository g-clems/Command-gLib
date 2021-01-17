package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.argument.ISubArgumentElementType;
import net.feedthemadness.glib.command.sub.argument.SubArgumentElementTypeString;

public class SubArgument extends ACommandElement {
	
	protected ISubArgumentElementType type = new SubArgumentElementTypeString();
	
	public SubArgument() {
	}
	
	@Override
	public SubArgument addSubElement(ACommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public SubArgument addExecutor(ICommandExecutor listener, String id) {
		super.addExecutor(listener, id);
		return this;
	}
	
	public ISubArgumentElementType getType() {
		return type;
	}
	
	public SubArgument setType(ISubArgumentElementType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		
		if(!type.validate(context.getRawArg(depth))) {
			return false;
		}
		
		context.setArgType(depth - 1, type);
		
		return true;
	}
	
}
