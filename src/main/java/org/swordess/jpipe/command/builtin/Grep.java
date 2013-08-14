package org.swordess.jpipe.command.builtin;

import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.IOUtils;
import org.swordess.jpipe.util.StringUtils;

//TODO improve options according to Linux grep
@CommandAnno(name = "grep", desc = "print lines matching a pattern", options = {
	@OptionAnno(name = "-n", desc = "prefix each line of output with the line number within its input"),
	@OptionAnno(name = "-v", desc = "in verbose format")
})
public class Grep extends Command {

	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		int lineNumberWidth = String.valueOf(lines.size()).length();
		
		String patternStr = options.get(options.size() - 1).getValue().toString();
		Pattern pattern = Pattern.compile(patternStr);
		
		List<String> matchedLines = new ArrayList<String>();
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			Matcher matcher = pattern.matcher(line);
			List<Integer> matchedIndexes = new ArrayList<Integer>();
			boolean match = false;
			while (matcher.find()) {
				match = true;
				matchedIndexes.add(matcher.start());
			}
			
			if (match) {
				if (!haveSupportedOptions()) {
					matchedLines.add(line);
				} else {
					boolean hasNOption = hasOption("-n");
					boolean hasVOption = hasOption("-v");
					
					if (hasVOption) {
						String empty = StringUtils.emptyStr(line.length());
						String cursors = StringUtils.replaceAll(empty, matchedIndexes, "^");
						
						if (hasNOption) {
							matchedLines.add(String.format("%" + lineNumberWidth + "d:%s", i+1, line));
							matchedLines.add(String.format("%" + (lineNumberWidth + 1)+ "s%s", "", cursors));
						} else {
							matchedLines.add(line);
							matchedLines.add(cursors);
						}
					} else if (hasNOption) {
						matchedLines.add(String.format("%" + lineNumberWidth + "d:%s", i+1, line));
					}
				}
			}
		}
		IOUtils.writeLines(writer, matchedLines);
		return Command.STATUS_SUCCESS;
	}

}
