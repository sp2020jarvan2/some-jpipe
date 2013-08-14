package org.swordess.jpipe;

import org.swordess.jpipe.command.CommandExecuter;
import org.swordess.jpipe.command.CommandManager;
import org.swordess.jpipe.command.builtin.Cat;
import org.swordess.jpipe.command.builtin.Echo;
import org.swordess.jpipe.command.builtin.Grep;
import org.swordess.jpipe.command.builtin.Sort;
import org.swordess.jpipe.command.builtin.Wc;
import org.swordess.jpipe.util.ArrayUtils;


public class Main {

	static {
		CommandManager.registCommand(Echo.class);
		CommandManager.registCommand(Cat.class);
		CommandManager.registCommand(Grep.class);
		CommandManager.registCommand(Sort.class);
		CommandManager.registCommand(Wc.class);
	}
	
	public static void main(String[] args) {
		if (ArrayUtils.isEmpty(args)) {
			System.err.println("-jpipe: no command provided");
			System.exit(0);
		}
		
		String shell = args[0];
		int statusCode = new CommandExecuter(shell).start();
		System.exit(statusCode);
	}
	
}
