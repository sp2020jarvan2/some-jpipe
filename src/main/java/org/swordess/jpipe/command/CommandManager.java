package org.swordess.jpipe.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.swordess.jpipe.command.CommandAnno.OptionAnno;
import org.swordess.jpipe.util.ArrayUtils;
import org.swordess.jpipe.util.DataStructures;
import org.swordess.jpipe.util.StringUtils;


public class CommandManager {

	private static Map<String, Class<? extends Command>> cmdNameToClass = DataStructures.newHashMap();
	private static Map<Class<? extends Command>, CommandMetadata> cmdClassToMetadata = DataStructures.newHashMap();
	
	private CommandManager() {
	}
	
	public static void registCommand(Class<? extends Command> cmdClass) {
		if (null == cmdClass || !cmdClass.isAnnotationPresent(CommandAnno.class)) {
			return;
		}
		
		CommandAnno cmdAnno = cmdClass.getAnnotation(CommandAnno.class);
		cmdNameToClass.put(cmdAnno.name(), cmdClass);
		cmdClassToMetadata.put(cmdClass, new CommandMetadata(cmdClass));
	}
	
	public static Command newCommand(String cmdName) {
		if (null == cmdName || cmdName.isEmpty()) {
			throw new IllegalArgumentException("omdName should not be null");
		}
		
		if (!cmdNameToClass.containsKey(cmdName)) {
			throw new UnregistedCommandException(cmdName + " is not registed");
		}
		
		Class<? extends Command> cmdClass = cmdNameToClass.get(cmdName);
		Command instance = null;
		try {
			instance = cmdClass.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return instance;
	}
	
	public static Collection<Class<? extends Command>> availableCommands() {
		return cmdNameToClass.values();
	}
	
	public static CommandMetadata getCommandMetadata(Class<? extends Command> cmdClass) {
		return cmdClassToMetadata.get(cmdClass);
	}
	
	public static class CommandMetadata {
		
		private final String name;
		private final String desc;
		private final List<OptionAnno> supportedOptions = new ArrayList<OptionAnno>();
		
		private CommandMetadata(Class<? extends Command> cmdClass) {
			// the existence of CommandAnno annotation has already been checked
			// during registCommand
			CommandAnno cmdAnno = cmdClass.getAnnotation(CommandAnno.class);
			this.name = cmdAnno.name();
			this.desc = cmdAnno.desc();
			if (ArrayUtils.isNotEmpty(cmdAnno.options())) {
				Collections.addAll(supportedOptions, cmdAnno.options());
			}
		}
		
		public String getName() {
			return name;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public boolean isSupportedOption(String optionName) {
			if (StringUtils.isEmpty(optionName)) {
				return false;
			}
			
			for (OptionAnno supportedOption : supportedOptions) {
				if (supportedOption.name().equals(optionName)) {
					return true;
				}
			}
			return false;
		}
		
	}
	
}
