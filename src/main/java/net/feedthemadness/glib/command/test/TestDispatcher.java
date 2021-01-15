package net.feedthemadness.glib.command.test;

import java.util.Random;

import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.dispatcher.CommandDispatcher;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.test.utils.Benchmark;
import net.feedthemadness.glib.command.test.utils.TerminalWrapper;

public class TestDispatcher implements ICommandDispatcher, ICommandExecutor {
	
	private TerminalWrapper tw = new TerminalWrapper();
	
	private CommandDispatcher commandDispatcher = new CommandDispatcher();
	
	@Override
	public CommandDispatcher getCommandDispatcher() {
		return commandDispatcher;
	}
	
	private volatile boolean stop;
	
	public void performBenchmark(int tests) {
		
		if(tests < 1000) {
			tw.warning("Could not perform less than 1000 tests");
			return;
		}
		
		tw.info("Performing " + tests + " tests");
		tw.log("Init tests");
		
		String[] labels = {"benchmark", "benchmark0", "benchmark1", "benchmark2", "benchmark3", "benchmark4", "benchmark5"};
		String[] args1 = {"ouqzbd", "yfsefgs", "oiqzhouqhf", "qugzfhihqef", "quzhuqozhfuses", "isehfs", "sjefn", "sienfsnefons", "jsbef"};
		
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
		
		tw.log("Start tests");
		
		Benchmark benchmark = new Benchmark(tests / 1000);
		Benchmark globalBenchmark = new Benchmark(1);
		
		for(int i = 0; i < parsableCommands.length ; i++) {
			commandDispatcher.dispatch(parsableCommands[i], this);
			if((i % 1000) + 1 == 0) benchmark.update();
		}
		
		globalBenchmark.update();
		
		tw.log("Done");
		
		tw.info("Total compute time : " + globalBenchmark.getAverageDelta());
		tw.info("Compute time per 1000 : min/avg/med/max = "
				+ Math.round(benchmark.getMinDelta()) + '/'
				+ Math.round(benchmark.getAverageDelta()) + '/'
				+ Math.round(benchmark.getMedianDelta()) + '/'
				+ Math.round(benchmark.getMaxDelta()) + " ms");
	}
	
	public void startListener() {
		while(!stop) {
			String str = tw.requestString("");
			tw.log("Dispatch");
			commandDispatcher.dispatch(str, this);
		}
	}
	
	@CommandListener("perform benchmark")
	public void performBenchmark(ICommandDispatcher dispatcher, CommandContext context, Command command, int tests) {
		performBenchmark(tests);
	}
	
	@CommandListener("benchmark")
	public void benchmark(ICommandDispatcher dispatcher, CommandContext context, Command command, String str, int a, int b, int c) {}
	
	@CommandListener("stop")
	public void stop(ICommandDispatcher dispatcher, CommandContext context, Command command) {
		stop = true;
	}
	
}
