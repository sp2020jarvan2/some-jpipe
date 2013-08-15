package org.swordess.jpipe.command.builtin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.swordess.jpipe.Debug;
import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.IOUtils;


// TODO improve options according to Linux cat
@CommandAnno(name = "cat", desc="concatenate files and print on the standard output", options = {
	@OptionAnno(name = "-n", desc = "number all output lines")
})
public class Cat extends Command {

	public Cat(Reader... sources) {
		super(sources);
	}
	
	@Override
	protected void prepare() {
		for (Option option : options) {
			if (!option.isStandard()) {
				String fileName = option.getValue().toString();
				File file = new File(fileName);
				if (!file.exists()) {
					Debug.err("cat", fileName, "no such file");
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
