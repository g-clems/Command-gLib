package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class CommandTest implements ICommandExecutor {
	
	@CommandListener("run gen iteration thread")
	public void cmdTest1(ICommandDispatcher dispatcher, CommandContext context, Command command, int gen, int iteration, int thread) {
		System.out.println("success");
	}
	
	@CommandListener("run gen name")
	public void cmdTest2(ICommandDispatcher dispatcher, CommandContext context, Command command, int gen, String name) {
		System.out.println("success");
	}
	
	@CommandListener("run name")
	public void cmdTest3(ICommandDispatcher dispatcher, CommandContext context, Command command, String name) {
		System.out.println("success");
	}
	
	@CommandListener("run name gen")
	public void cmdTest4(ICommandDispatcher dispatcher, CommandContext context, Command command, String name, int gen) {
		System.out.println("success");
	}
	
}
