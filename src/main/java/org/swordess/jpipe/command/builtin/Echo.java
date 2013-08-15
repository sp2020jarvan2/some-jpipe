package org.swordess.jpipe.command.builtin;

import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.util.CollectionUtils;
import org.swordess.jpipe.util.IOUtils;

// TODO improve options according to Linux echo
@CommandAnno(name = "echo", desc = "display line of text")
public class Echo extends Command {
	
	public Echo(Reader[] sources) {
		super(sources);
	}

	@Override
	protected void prepare() {
		StringBuilder text = new StringBuilder();
		if (CollectionUtils.isNotEmpty(options)) {
			for (int i = 0; i < options.size(); i++) {
				Option option = options.get(i);
				if (!option.isStandard()) {
					text.append(option.getName());
					if (i != (options.size() - 1)) {
						text.append(" ");
					}
				}
			}
		}
		source(new StringReader(text.toString()));
	}

	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		IOUtils.writeLines(writer, lines);
		return Command.STATUS_SUCCESS;
	}

}
