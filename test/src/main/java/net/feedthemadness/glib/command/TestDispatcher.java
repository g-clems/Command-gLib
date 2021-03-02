package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.CommandDispatcher;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.executor.listener.CmdArg;
import net.feedthemadness.glib.command.executor.listener.CmdContext;
import net.feedthemadness.glib.command.utils.Benchmark;

import java.util.Map;
import java.util.Random;

public class TestDispatcher implements ICommandDispatcher, ICommandExecutor {
	
	private CommandDispatcher commandDispatcher = new CommandDispatcher();
	
	@Override
	public ICommandDispatcher addCommand(Command command) {
		return commandDispatcher.addCommand(command);
	}
	
	@Override
	public CommandDispatcher addUsageExecutor(ICommandExecutor executor, String id) {
		return commandDispatcher.addUsageExecutor(executor, id);
	}
	
	@Override
	public void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Map<String, Object> dispatchContext) {
		commandDispatcher.dispatch(dispatcher, parsableCommand, dispatchContext);
	}
	
	private volatile boolean stop;
	
	public void performBenchmark(int tests) {
		
		if(tests < 1000) {
			Main.getTerminal().warning("Could not perform less than 1000 tests");
			return;
		}
		
		Main.getTerminal().info("Performing " + tests + " tests");
		Main.getTerminal().log("Init tests");
		
		String[] labels = {"benchmark", "benchmark0", "benchmark1", "benchmark2", "benchmark3", "benchmark4", "benchmark5"};
		String[] args1 = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9"};
		
		String[] parsableCommands = new String[tests];
		
		Random random = new Random();
		
		for(int i = 0 ; i < parsableCommands.length ; i++) {
			StringBuilder builder = new StringBuilder();
			
			builder.append('/').append(labels[random.nextInt(labels.length)]).append(' ');
			builder.append(args1[random.nextInt(args1.length)]).append(' ');
			builder.append(random.nextInt()).append(' ');
			builder.append(random.nextInt()).append(' ');
			builder.append(random.nextInt());
			
			parsableCommands[i] = builder.toString();
		}
		
		Main.getTerminal().log("Start tests");
		
		Benchmark benchmark = new Benchmark(tests / 1000);
		Benchmark globalBenchmark = new Benchmark(1);
		
		for(int i = 0 ; i < parsableCommands.length ; i++) {
			commandDispatcher.dispatch(this, parsableCommands[i], null);
			if((i - 1 % 1000) == 0) benchmark.update();
		}
		
		globalBenchmark.update();
		
		Main.getTerminal().log("Done");
		
		Main.getTerminal().info("Total compute time : " + globalBenchmark.getAverageDelta());
		Main.getTerminal().info("Compute time per 1000 : min/avg/med/max = "
				+ Math.round(benchmark.getMinDelta()) + '/'
				+ Math.round(benchmark.getAverageDelta()) + '/'
				+ Math.round(benchmark.getMedianDelta()) + '/'
				+ Math.round(benchmark.getMaxDelta()) + " ms");
	}
	
	public void startListener() {
		while(!stop) {
			String str = Main.getTerminal().requestString("");
			commandDispatcher.dispatch(this, str, null);
		}
	}
	
	@CommandListener("perform benchmark")
	public void performBenchmark(@CmdContext CommandContext context, @CmdArg("iterations") int tests) {
		performBenchmark(tests);
	}
	
	@CommandListener("benchmark")
	public void benchmark(@CmdContext CommandContext context, @CmdArg("str") String str, @CmdArg("a") int a, @CmdArg("b") int b, @CmdArg("c") int c) {
	}
	
	@CommandListener("stop")
	public void stop(@CmdContext CommandContext context) {
		stop = true;
	}
	
}
