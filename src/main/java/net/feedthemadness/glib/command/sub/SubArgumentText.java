package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.Main;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class SubArgumentText extends ASubCommandElement {
	
	public SubArgumentText(String name) {
		super(name);
	}
	
	@Override
	public SubArgumentText addSubElement(ACommandElement subElement) {
		Main.getTerminal().warning("Could not add SubElement in SubArgumentText");
		//TODO proper error
		return this;
	}
	
	@Override
	public SubArgumentText addExecutor(ICommandExecutor executor, String id) {
		super.addExecutor(executor, id);
		return this;
	}
	
	@Override
	protected boolean dispatch(CommandContext context, int depth) {
		depth = context.parsableArgumentsSize() - 1;
		return super.dispatch(context, depth);
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		context.putArgument(name, parseArgs(context, depth));
		
		return true;
	}
	
	private String parseArgs(CommandContext context, int depth) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = depth ; i < context.parsableArgumentsSize() ; i++) {
			builder.append(context.getParsableArgument(i));
			if(i < context.parsableArgumentsSize() - 1) builder.append(' ');
		}
		
		return builder.toString();
	}
	
}
