package net.feedthemadness.glib.command.sub.argument;

public class SubArgumentElementTypeString extends ASubArgumentElementType {
	
	@Override
	public boolean validate(String parsableArgument) {
		try {
			Double.parseDouble(parsableArgument);
		} catch(NumberFormatException e) {
			return true;
		}
		return false;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
