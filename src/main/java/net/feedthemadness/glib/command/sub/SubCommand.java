package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.command.ISubCommandElementType;
import net.feedthemadness.glib.command.sub.command.SubCommandElementTypeLabel;
import net.feedthemadness.glib.command.sub.command.SubCommandElementTypeString;

public class SubCommand extends ACommandElement {
	
	protected ISubCommandElementType type = new SubCommandElementTypeString();
	
	public SubCommand() {}
	
	public SubCommand(String label) {
		this.type = new SubCommandElementTypeLabel(label);
	}
	
	public SubCommand(String label, String... aliases) {
		this.type = new SubCommandElementTypeLabel(label, aliases);
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
	
	public ISubCommandElementType getType() {
		return type;
	}
	
	public SubCommand setType(ISubCommandElementType type) {
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
