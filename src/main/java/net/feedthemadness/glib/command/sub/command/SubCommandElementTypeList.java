package net.feedthemadness.glib.command.sub.command;

import java.util.Arrays;

public class SubCommandElementTypeList extends ASubCommandElementType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return false;
	}
	
	@Override
	public Object parse(String str) {
		return Arrays.asList(str);
	}
	
}
