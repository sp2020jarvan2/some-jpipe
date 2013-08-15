package org.swordess.jpipe.command;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.swordess.jpipe.util.ArrayUtils;
import org.swordess.jpipe.util.CollectionUtils;
import org.swordess.jpipe.util.DataStructures;
import org.swordess.jpipe.util.IOUtils;
import org.swordess.jpipe.util.StringUtils;

public abstract class Command implements Callable<Integer> {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_GENERAL_ERROR = 1;
	
	private List<Reader> sources = new ArrayList<Reader>();
	private PipedReader reader;
	private PipedWriter writer;
	
	protected List<Option> options = DataStructures.newArrayList();
	private List<Option> supportedOptions = null;
	
	public Command(Reader[] sources) {
		if (ArrayUtils.isNotEmpty(sources)) {
			for (Reader source : sources) {
				source(source);
			}
		}
		reader = new PipedReader();
		try {
			writer = new PipedWriter(reader);
		} catch (IOException e) {
		}
	}
	
	protected abstract int processLines(List<String> lines, PipedWriter writer);
	
	@Override
	public Integer call() {
		if (null == writer) {
			return Command.STATUS_GENERAL_ERROR;
		}
		
		prepare();
		
		try {
			List<String> lines = new ArrayList<String>();
			for (Reader source : sources) {
				lines.addAll(IOUtils.readLines(source));
			}
			
			int statusCode = processLines(lines, writer);
			writer.close(); // make sure the writer has been closed
			
			return statusCode;
			
		} catch (IOException e) {
			return Command.STATUS_GENERAL_ERROR;
		}
	}

	protected void prepare() {
	}
	
	protected final Command source(Reader source) {
		if (null != source) {
			sources.add(source);
		}
		return this;
	}
	
	public Command option(String option) {
		if (StringUtils.isNotEmpty(option)) {
			int indexOfEqualSign = option.indexOf('=');
			if (indexOfEqualSign != -1) {
				String optionName = option.substring(0, indexOfEqualSign);
				String optionValue = option.substring(indexOfEqualSign + 1);
				options.add(new Option(optionName, optionValue));
			} else {
				options.add(new Option(option));
			}
		}
		return this;
	}
	
	public Command options(String[] options) {
		if (ArrayUtils.isNotEmpty(options)) {
			for (String option : options) {
				option(option);
			}
		}
		return this;
	}
	
	public boolean hasOption(String optionName) {
		if (StringUtils.isEmpty(optionName)) {
			return false;
		}
		
		for (Option option : options) {
			if (option.isSupported() && option.getName().equals(optionName)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Option> getSupportedOptions() {
		if (null == supportedOptions) {
			supportedOptions = new ArrayList<Option>();
			for (Option option : options) {
				if (option.isSupported()) {
					supportedOptions.add(option);
				}
			}
		}
		return supportedOptions;
	}
	
	public boolean haveSupportedOptions() {
		return CollectionUtils.isNotEmpty(getSupportedOptions());
	}
	
	public Reader getResult() {
		return reader;
	}
	
	public class Option {
		
		private String name;
		private Object value;
		
		private boolean standard;
		private boolean supported;
		
		private Option(String name) {
			this.name = name;
			initStandardAndSupported();
			
			this.value = (supported ? Boolean.TRUE : name);
		}
		
		private Option(String name, Object value) {
			this.name = name;
			initStandardAndSupported();
			
			this.value = value;
		}
		
		private void initStandardAndSupported() {
			standard = name.startsWith("-");
			supported = CommandManager.getCommandMetadata(Command.this.getClass())
					.isSupportedOption(name);
		}
		
		public String getName() {
			return this.name;
		}
		
		public Object getValue() {
			return this.value;
		}
		
		public boolean isStandard() {
			return standard;
		}
		
		public boolean isSupported() {
			return supported;
		}
		
		@Override
		public String toString() {
			return isSupported() ? name + "=" + value : value.toString();
		}
		
	}
	
}
