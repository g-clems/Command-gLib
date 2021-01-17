package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.dispatcher.CommandRegister;
import net.feedthemadness.glib.command.sub.SubArgument;
import net.feedthemadness.glib.command.sub.argument.SubArgumentElementTypeInt;
import net.feedthemadness.glib.command.sub.argument.SubArgumentElementTypeString;
import net.feedthemadness.glib.command.test.TestDispatcher;
import net.feedthemadness.glib.command.test.privateroom.CommandManager;
import net.feedthemadness.glib.command.test.utils.TerminalWrapper;

public class Main {
	
	private static TerminalWrapper tw = new TerminalWrapper();
	
	public static TerminalWrapper getTerminal() {
		return tw;
	}
    
	public static void main(String[] args) {
		registerCommands();
	}
	
	public static void registerCommands() {
		TestDispatcher dispatcher = new TestDispatcher();
		
		CommandRegister.registerCommand(new Command().setPrefix("/").setLabel("stop").addExecutor(dispatcher, "stop"), dispatcher);
		
		CommandRegister.registerCommand(
				new Command()
				.setPrefix("/")
				.setLabelAndAliases("benchmark", "benchmark0", "benchmark1", "benchmark2", "benchmark3", "benchmark4", "benchmark5")
				.addSubElement(
						new SubArgument()
						.setType(new SubArgumentElementTypeInt())
						.addExecutor(dispatcher, "perform benchmark")
						)
				.addSubElement(
						new SubArgument()
						.setType(new SubArgumentElementTypeString())
						.addSubElement(
								new SubArgument()
								.setType(new SubArgumentElementTypeInt())
								.addSubElement(
										new SubArgument()
										.setType(new SubArgumentElementTypeInt())
										.addSubElement(
												new SubArgument()
												.setType(new SubArgumentElementTypeInt())
												.addExecutor(dispatcher, "benchmark")
												)
										)
								)
						),
				dispatcher);
		
		new CommandManager().registers(dispatcher);
		
		dispatcher.startListener();
	}
}
