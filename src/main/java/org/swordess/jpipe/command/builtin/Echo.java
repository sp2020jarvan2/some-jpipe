package org.swordess.jpipe.command.builtin;

import java.io.PipedWriter;
import java.util.List;

import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.util.IOUtils;

@CommandAnno(name = "echo", desc = "display lines of text")
public final class Echo extends Command {

	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		IOUtils.writeLines(writer, lines);
		return Command.STATUS_SUCCESS;
	}

}
