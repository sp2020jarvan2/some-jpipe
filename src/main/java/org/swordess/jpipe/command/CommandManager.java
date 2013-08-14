package org.swordess.jpipe.command;

import java.util.Collection;
import java.util.Map;

import org.swordess.jpipe.util.DataStructures;


public class CommandManager {

	private static Map<String, Class<? extends Command>> nameToCommand = DataStructures.newHashMap();
	
	private CommandManager() {
	}
	
	public static void registCommand(Class<? extends Command> cmdClass) {
		if (null == cmdClass || !cmdClass.isAnnotationPresent(CommandAnno.class)) {
			return;
		}
		
		CommandAnno cmdAnno = cmdClass.getAnnotation(CommandAnno.class);
		nameToCommand.put(cmdAnno.name(), cmdClass);
	}
	
	public static Command getCommand(String cmdName) {
		if (null == cmdName || cmdName.isEmpty()) {
			throw new IllegalArgumentException("cmdKey should not be null");
		}
		
		if (!nameToCommand.containsKey(cmdName)) {
			throw new UnregistedCommandException(cmdName + " is not registed");
		}
		
		Class<? extends Command> cmdClass = nameToCommand.get(cmdName);
		Command instance = null;
		try {
			instance = cmdClass.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return instance;
	}
	
	public static Collection<Class<? extends Command>> availableCommands() {
		return nameToCommand.values();
	}
	
}
