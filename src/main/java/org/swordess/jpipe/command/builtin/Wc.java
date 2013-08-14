package org.swordess.jpipe.command.builtin;

import java.io.PipedWriter;
import java.util.List;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.IOUtils;

//TODO improve options according to Linux wc
@CommandAnno( name = "wc", desc = "print the number of newlines, words", options = {
	@OptionAnno(name = "-l", desc = "print the newline counts"),
	@OptionAnno(name = "-w", desc = "print the word counts")
})
public class Wc extends Command {

	private int lineCount = 0;
	private int wordCount = 0;
	
	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		String outputFormat = null;
		Object[] outputArgs = null;
		
		if (!haveSupportedOptions()) {
			countLines(lines);
			countWords(lines);
			outputFormat = getDefaultOutputFormat();
			outputArgs = getDefaultOutputArgs();
			
		} else {
			boolean hasLOption = hasOption("-l");
			boolean hasWOption = hasOption("-w");
			
			if (hasLOption) {
				countLines(lines);
			}
			if (hasWOption) {
				countWords(lines);
			}
			
			if (hasLOption && hasWOption) {
				outputFormat = getDefaultOutputFormat();
				outputArgs = getDefaultOutputArgs();
			} else if (hasLOption) {
				outputFormat = "%d";
				outputArgs = new Object[] { lineCount };
			} else if (hasWOption) {
				outputFormat = "%d";
				outputArgs = new Object[] { wordCount };
			}
		}
		
		IOUtils.writeLine(writer, String.format(outputFormat, outputArgs));
		return Command.STATUS_SUCCESS;
	}
	
	private void countLines(List<String> lines) {
		lineCount = lines.size();
	}
	
	private void countWords(List<String> lines) {
		for (String line : lines) {
			wordCount += line.trim().split("\\w").length;
		}
	}
	
	private String getDefaultOutputFormat() {
		int fieldWidth = String.valueOf(Math.max(lineCount, wordCount)).length() + 1;
		return "%" + fieldWidth + "d %" + fieldWidth + "d";
	}
	
	private Object[] getDefaultOutputArgs() {
		return new Object[] { lineCount, wordCount };
	}

}
