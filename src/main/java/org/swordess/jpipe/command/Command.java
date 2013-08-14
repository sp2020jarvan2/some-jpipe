package org.swordess.jpipe.command;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.swordess.jpipe.util.IOUtils;

public abstract class Command {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_GENERAL_ERROR = 1;
	
	private Reader source;
	private PipedReader reader;
	
	protected List<String> options = new ArrayList<String>();
	
	protected abstract int processLines(List<String> lines, PipedWriter writer);
	
	public int run() {
		try {
			List<String> lines = IOUtils.readLines(source);
			
			reader = new PipedReader();
			PipedWriter writer = new PipedWriter(reader);
			int statusCode = processLines(lines, writer);
			writer.close(); // make sure the writer has been closed
			
			return statusCode;
			
		} catch (IOException e) {
			return Command.STATUS_GENERAL_ERROR;
		}
	}
	
	public Command source(Reader source) {
		this.source = source;
		return this;
	}
	
	public Command option(String option) {
		if (null != option && !option.isEmpty()) {
			options.add(option);
		}
		return this;
	}
	
	public Command options(String[] options) {
		if (null != options && options.length != 0) {
			for (String option : options) {
				option(option);
			}
		}
		return this;
	}
	
	public boolean hasOption(String option) {
		return options.contains(option);
	}
	
	public Reader getResult() {
		return reader;
	}
	
}
