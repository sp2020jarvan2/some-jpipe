package org.swordess.jpipe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandManager;
import org.swordess.jpipe.command.builtin.BuiltIns;
import org.swordess.jpipe.util.ArrayUtils;
import org.swordess.jpipe.util.IOUtils;
import org.swordess.jpipe.util.StringUtils;


public class Main {

	static {
		BuiltIns.registerCommands();
	}
	
	public static void main(String[] args) {
		if (ArrayUtils.isEmpty(args)) {
			// print help messages
			System.exit(0);
		}
		
		if (StringUtils.isEmpty(args[0])) {
			System.out.println("-jpipe: no data file provided");
			System.exit(0);
		}
		
		String dataFile = args[0];
		try {
			Reader currentSource = new FileReader(dataFile);
			
			List<String> tokens = new ArrayList<String>();
			if (args.length > 1) {
				for (int i = 1; i < args.length; i++) {
					String arg = args[i].trim();
					if (arg.equals("|")) {
						continue;
					}
					tokens.add(arg);
				}
			}
			
			Command currentCmd = null;
			int currentStatusCode = Command.STATUS_SUCCESS;
			for (String token : tokens) {
				String[] pieces = token.split("\\s");
				
				String cmdKey = pieces[0];
				currentCmd = CommandManager.getCommand(cmdKey).source(currentSource);
				if (pieces.length >= 2) {
					currentCmd.options(Arrays.copyOfRange(pieces, 1, pieces.length));
				}
				
				currentStatusCode = currentCmd.run();
				if (Command.STATUS_SUCCESS == currentStatusCode) {
					currentSource = currentCmd.getResult();
				}
			}
			
			if (Command.STATUS_SUCCESS == currentStatusCode) {
				if (currentSource != null) {
					// finally, output to the standard output stream
					for (String line : IOUtils.readLines(currentSource)) {
						System.out.println(line);
					}
				}
				System.exit(0);
			} else {
				System.exit(currentStatusCode);
			}
			
		} catch (FileNotFoundException e) {
			System.out.printf("-jpipe: %s: no such file%n", dataFile);
			System.exit(0);
		}
	}
	
}
