package net.feedthemadness.glib.command.test.privateroom;

import net.feedthemadness.glib.command.sub.argument.ISubArgumentType;

public class CommandTypeIdMention implements ISubArgumentType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parse(parsableArgument);
	}
	
}
