package org.swordess.jpipe.command.builtin;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;

import org.swordess.jpipe.command.Command;
import org.swordess.jpipe.command.CommandAnno;
import org.swordess.jpipe.command.CommandManager;

public class BuiltIns {
	
	private BuiltIns() {
	}
	
	public static void registerCommands() {
		Package builtinPkg = BuiltIns.class.getPackage();
		URL pkgURL = BuiltIns.class.getClassLoader().getResource(builtinPkg.getName().replace('.', File.separatorChar));
		
		try {
			File[] classFiles = new File(pkgURL.toURI()).listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});
			
			for (File f : classFiles) {
				String simpleClassName = f.getName().substring(0, f.getName().indexOf(".class"));
				String qualifiedClassName = builtinPkg.getName() + "." + simpleClassName;
				try {
					Class<?> clazz = BuiltIns.class.getClassLoader().loadClass(qualifiedClassName);
					if (clazz.getSuperclass() == Command.class) {
						@SuppressWarnings("unchecked")
						Class<? extends Command> cmdClass = (Class<? extends Command>) clazz;
						if (clazz.isAnnotationPresent(CommandAnno.class)) {
							CommandManager.registCommand(cmdClass);
						}
					}
				} catch (ClassNotFoundException e) {
				}
			}
		} catch (URISyntaxException e) {
		}
	}
	
}
