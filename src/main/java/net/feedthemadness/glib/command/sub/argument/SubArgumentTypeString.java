package net.feedthemadness.glib.command.sub.argument;

public class SubArgumentTypeString extends ASubArgumentType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
