package org.swordess.jpipe.command.builtin;

import java.io.PipedWriter;
import java.util.Collections;
import java.util.List;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.IOUtils;

//TODO improve options according to Linux sort
@CommandAnno(name = "sort", desc = "sort lines of text files", options = {
	@OptionAnno(name = "-r", desc = "reverse the result of comparisons")
})
public class Sort extends Command {

	@Override
	protected int processLines(List<String> lines, PipedWriter writer) {
		Collections.sort(lines);
		if (hasOption("-r")) {
			Collections.reverse(lines);
		}
		IOUtils.writeLines(writer, lines);
		return Command.STATUS_SUCCESS;
	}

}
