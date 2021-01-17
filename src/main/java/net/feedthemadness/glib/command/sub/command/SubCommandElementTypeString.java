package net.feedthemadness.glib.command.sub.command;

public class SubCommandElementTypeString extends ASubCommandElementType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
