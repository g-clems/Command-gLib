package net.feedthemadness.glib.command.sub.command;

import net.feedthemadness.glib.command.component.CommandLabel;

public class SubCommandElementTypeLabel extends CommandLabel implements ISubCommandElementType {
	
	public SubCommandElementTypeLabel() {}
	
	public SubCommandElementTypeLabel(String label) {
		super(label);
	}
	
	public SubCommandElementTypeLabel(String label, String... aliases) {
		super(label, aliases);
	}
	
	@Override
	public boolean validate(String parsableArgument) {
		return check(parsableArgument);
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument.toLowerCase();
	}
	
}
