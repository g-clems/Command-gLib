package net.feedthemadness.glib.command.privateroom;

import net.feedthemadness.glib.command.sub.argument.ISubArgumentType;

public class CommandTypeRole implements ISubArgumentType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
