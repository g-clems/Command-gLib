package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.dispatcher.CommandRegister;
import net.feedthemadness.glib.command.sub.SubArgument;
import net.feedthemadness.glib.command.sub.argument.SubArgumentTypeInt;
import net.feedthemadness.glib.command.sub.argument.SubArgumentTypeString;
import net.feedthemadness.glib.command.privateroom.CommandManager;
import net.feedthemadness.glib.command.utils.TerminalWrapper;

public class Main {
	
	private static TerminalWrapper tw = new TerminalWrapper(true);
	
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
				.setLabelAndAliases("benchmark", "bm", "benchmark0", "benchmark1", "benchmark2", "benchmark3", "benchmark4", "benchmark5")
				.addSubElement(
						new SubArgument("iterations")
						.setType(new SubArgumentTypeInt())
						.addExecutor(dispatcher, "perform benchmark")
						)
				.addSubElement(
						new SubArgument("str")
						.setType(new SubArgumentTypeString())
						.addSubElement(
								new SubArgument("a")
								.setType(new SubArgumentTypeInt())
								.addSubElement(
										new SubArgument("b")
										.setType(new SubArgumentTypeInt())
										.addSubElement(
												new SubArgument("c")
												.setType(new SubArgumentTypeInt())
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
