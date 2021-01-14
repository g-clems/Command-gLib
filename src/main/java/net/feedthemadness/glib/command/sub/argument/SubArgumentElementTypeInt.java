package net.feedthemadness.glib.command.sub.argument;

public class SubArgumentElementTypeInt extends ASubArgumentElementType {
	
	@Override
	public boolean validate(String parsableArgument) {
		try {
			Integer.parseInt(parsableArgument);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object parse(String str) {
		return Integer.parseInt(str);
	}
	
}
