package org.swordess.jpipe;

public final class Debug {

	private Debug() {
	}
	
	public static void err(String msg) {
		System.err.format("-jpipe: %s%n", msg);
	}
	
	public static void err(String section, String msg) {
		System.err.format("-jpipe: %s: %s%n", section, msg);
	}
	
	public static void err(String section, String subSection, String msg) {
		System.err.format("-jpipe: %s: %s: %s%n", section, subSection, msg);
	}
	
}
