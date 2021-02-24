package net.feedthemadness.glib.command.component;

import net.feedthemadness.glib.command.dispatcher.CommandContext;

import java.util.Arrays;

public class CommandLabel {
	
	private String label = "";
	private String[] aliases = new String[0];
	
	public CommandLabel() {
	}
	
	public CommandLabel(String label) {
		this.label = label;
	}
	
	public CommandLabel(String label, String... aliases) {
		this.label = label;
		this.aliases = aliases;
	}
	
	public String getLabel() {
		return label;
	}
	
	public CommandLabel setLabel(String label) {
		this.label = label;
		return this;
	}
	
	public String[] getAliases() {
		return Arrays.copyOf(aliases, aliases.length);
	}
	
	public CommandLabel setAliases(String... aliases) {
		this.aliases = aliases;
		return this;
	}
	
	public CommandLabel addAliases(String... aliases) {
		String[] newAliases = Arrays.copyOf(this.aliases, this.aliases.length + aliases.length);
		
		System.arraycopy(aliases, 0, newAliases, this.aliases.length, aliases.length);
		
		this.aliases = newAliases;
		return this;
	}
	
	public boolean compare(CommandContext context, int prefixLength) {
		String contextLabel = context.getParsableArgument(0).substring(prefixLength);
		
		return compare(contextLabel);
	}
	
	public boolean compare(String label) {
		if(this.label.equalsIgnoreCase(label)) return true;
		
		for(int i = 0 ; i < aliases.length ; i++) {
			String alias = aliases[i];
			if(alias.equalsIgnoreCase(label)) return true;
		}
		return false;
	}
	
}
