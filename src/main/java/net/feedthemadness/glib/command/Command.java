package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.component.CommandLabel;
import net.feedthemadness.glib.command.component.CommandPrefix;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.ASubCommandElement;

public class Command extends ACommandElement {

//	private Optional<String> name = Optional.empty();
	
	private CommandPrefix prefix = new CommandPrefix();
	private CommandLabel label = new CommandLabel();
	
	public Command() {
	}
	
	public Command(String prefix, String label) {
		setPrefix(prefix);
		setLabelAndAliases(label);
	}
	
	public Command(String prefix, String label, String... aliases) {
		setPrefix(prefix);
		setLabelAndAliases(label, aliases);
	}
	
	public CommandPrefix getPrefix() {
		return prefix;
	}
	
	public Command setPrefix(String prefix) {
		this.prefix = new CommandPrefix(prefix);
		return this;
	}
	
	public Command setPrefix(CommandPrefix prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public CommandLabel getLabel() {
		return label;
	}
	
	public Command setLabel(String label) {
		this.label.setLabel(label);
		return this;
	}
	
	public Command addAliases(String... aliases) {
		this.label.addAliases(aliases);
		return this;
	}
	
	public Command setLabelAndAliases(String label, String... aliases) {
		this.label = new CommandLabel(label, aliases);
		return this;
	}
	
	public Command setLabel(CommandLabel label) {
		this.label = label;
		return this;
	}
	
	@Override
	public Command addSubElement(ASubCommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public Command addExecutor(ICommandExecutor executor, String id) {
		super.addExecutor(executor, id);
		return this;
	}
	
	@Override
	public Command addUsageExecutor(ICommandExecutor executor, String id) {
		super.addUsageExecutor(executor, id);
		return this;
	}
	
	public boolean dispatch(CommandContext context) {
		if(!checkDispatch(context, 0)) return false;
		dispatch(context, 0);
		return true;
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		if(!prefix.compare(context)) return false;
		return label.compare(context, prefix.getPrefix().length());
	}
	
}
