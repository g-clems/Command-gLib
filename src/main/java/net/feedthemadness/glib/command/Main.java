package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.dispatcher.CommandRegister;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.SubArgument;
import net.feedthemadness.glib.command.sub.SubCommand;
import net.feedthemadness.glib.command.sub.argument.SubArgumentElementTypeInt;
import net.feedthemadness.glib.command.sub.argument.SubArgumentElementTypeString;
import net.feedthemadness.glib.command.sub.command.SubCommandElementTypeList;
import net.feedthemadness.glib.command.test.CommandTest;
import net.feedthemadness.glib.command.test.TestDispatcher;

public class Main {
    
	public static void main(String[] args) {
		
		TestDispatcher dispatcher = new TestDispatcher();
		
		ICommandExecutor executor = new CommandTest();
		
		CommandRegister.registerCommand(
				new Command()// ai run
				.setPrefix("/")
				.setLabelAndAliases("run", "r")
				.addSubElement(
						new SubArgument()// /run <gen>
						.setType(new SubArgumentElementTypeInt())
						.addSubElement(
								new SubArgument()// /run <gen> <iterations>
								.setType(new SubArgumentElementTypeInt())
								.addSubElement(
										new SubArgument()// /run <gen> <iterations> <threads>
										.setType(new SubArgumentElementTypeInt())
										.addExecutor(executor, "run gen iteration thread")
										)
								)
						.addSubElement(
								new SubArgument()// /run <gen> <name>
								.setType(new SubArgumentElementTypeString())
								.addExecutor(executor, "run gen name"))
						)
				.addSubElement(
						new SubCommand()// /run <name>
						.setType(new SubCommandElementTypeList())
						.addExecutor(executor, "run name")
						.addSubElement(
								new SubArgument()// /run <name> <gen>
								.setType(new SubArgumentElementTypeInt())
								.addExecutor(executor, "run name gen"))),
				dispatcher);
		CommandRegister.registerCommand(new Command().setPrefix("/").setLabel("stop").addExecutor(dispatcher, "stop"), dispatcher);
		
		dispatcher.startListener();
	}
}
