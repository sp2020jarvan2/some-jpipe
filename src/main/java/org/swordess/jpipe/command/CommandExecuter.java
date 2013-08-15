package org.swordess.jpipe.command;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.swordess.jpipe.Debug;
import org.swordess.jpipe.util.DataStructures;
import org.swordess.jpipe.util.IOUtils;

public class CommandExecuter {

	private final String shell;
	
	public CommandExecuter(String shell) {
		this.shell = shell;
	}
	
	public int start() {
		final StatusCodeHolder holder = new StatusCodeHolder();
		final ExecutorService executor = Executors.newFixedThreadPool(10);
		final List<Map.Entry<Command, Future<Integer>>> cmdAndStatusCodePairs = DataStructures.newArrayList();
		
		Reader lastSource = null;
		for (String token : shell.split("\\|")) {
			String[] pieces = token.trim().split("\\s");
			
			String cmdName = pieces[0];
			Command cmd = CommandManager.newCommand(cmdName, lastSource);
			if (null == cmd) {
				return Command.STATUS_GENERAL_ERROR;
			}
			
			if (pieces.length > 1) {
				cmd.options(Arrays.copyOfRange(pieces, 1, pieces.length));
			}
			
			cmdAndStatusCodePairs.add(DataStructures.newSimpleEntry(cmd, executor.submit(cmd.source(lastSource))));
			lastSource = cmd.getResult();
		}
		
		try {
			// wait until all submitted commands have been completed
			new Thread() {
				public void run() {
					for (Map.Entry<Command, Future<Integer>> pair : cmdAndStatusCodePairs) {
						try {
							int codeOfThisCmd = pair.getValue().get().intValue();
							if (Command.STATUS_SUCCESS != codeOfThisCmd) {
								holder.statusCode = codeOfThisCmd;
								executor.shutdownNow();
							}
						} catch (InterruptedException e) {
							Debug.err(CommandManager.getCommandMetadata(pair.getKey().getClass()).getName(), e.getMessage());
						} catch (ExecutionException e) {
							Debug.err(CommandManager.getCommandMetadata(pair.getKey().getClass()).getName(), e.getMessage());
						}
					}
				}
			}.join();
			executor.shutdown();
		} catch (InterruptedException e) {
			Debug.err("command executer", e.getMessage());
		}
		
		if (lastSource != null) {
			IOUtils.writeLines(new PrintWriter(System.out), IOUtils.readLines(lastSource));
		}
		
		return holder.statusCode;
	}
	
	private static class StatusCodeHolder {
		
		int statusCode = Command.STATUS_SUCCESS;
		
	}
	
}
