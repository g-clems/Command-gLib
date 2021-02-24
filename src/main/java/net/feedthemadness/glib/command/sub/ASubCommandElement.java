package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;

public abstract class ASubCommandElement extends ACommandElement {
	
	protected final String name;
	
	public ASubCommandElement(String name) {
		this.name = name;
	}
	
}
