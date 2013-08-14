package org.swordess.jpipe.command;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.swordess.jpipe.util.IOUtils;

public class CommandExecuter {

	private List<Command> cmds = new ArrayList<Command>();
	
	public CommandExecuter(String shell) {
		cmds.addAll(parseCommands(shell));
	}
	
	public int start() {
		Reader lastSource = null;
		int statusCode = Command.STATUS_SUCCESS;
		for (Command cmd : cmds) {
			statusCode = cmd.source(lastSource).run();
			if (Command.STATUS_SUCCESS == statusCode) {
				lastSource = cmd.getResult();
			}
		}
		
		if (Command.STATUS_SUCCESS == statusCode) {
			if (lastSource != null) {
				IOUtils.writeLines(new PrintWriter(System.out), IOUtils.readLines(lastSource));
			}
		}
		
		return statusCode;
	}
	
	private static List<Command> parseCommands(String shell) {
		List<Command> commands = new ArrayList<Command>();
		for (String token : shell.split("\\|")) {
			String[] pieces = token.trim().split("\\s");
			String cmdName = pieces[0];
			Command cmd = CommandManager.newCommand(cmdName);
			if (pieces.length > 1) {
				cmd.options(Arrays.copyOfRange(pieces, 1, pieces.length));
			}
			commands.add(cmd);
		}
		return commands;
	}
	
}
