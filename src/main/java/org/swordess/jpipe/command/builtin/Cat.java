package org.swordess.jpipe.command.builtin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.List;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.IOUtils;


// TODO add description
// TODO improve options according to Linux cat
@CommandAnno(name = "cat", options = {
	@OptionAnno(name = "-n", desc = "...")
})
public class Cat extends Command {

	@Override
	protected void prepare() {
		for (Option option : options) {
			if (!option.isStandard()) {
				String fileName = option.getValue().toString();
				File file = new File(fileName);
				if (!file.exists()) {
					System.err.printf("-jpipe: cat: %s: no such file%n", fileName);
				}
				
				try {
					source(new FileReader(file));
				} catch (FileNotFoundException e) {
					// already checked above
				}
			}
		}
	}
	
	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		if (!haveSupportedOptions()) {
			IOUtils.writeLines(writer, lines);
			
		} else {
			List<String> processedLines = new ArrayList<String>();
			if (hasOption("-n")) {
				String outputFormat = null;
				int lineNumberWidth = String.valueOf(lines.size()).length();
				outputFormat = "%" + lineNumberWidth + "d:%s";
				for (int i = 0; i < lines.size(); i++) {
					processedLines.add(String.format(outputFormat, i+1, lines.get(i)));
				}
			}
			IOUtils.writeLines(writer, processedLines);
		}
		
		return Command.STATUS_SUCCESS;
	}

}
