package org.swordess.jpipe.command;

@SuppressWarnings("serial")
public class UnregistedCommandException extends RuntimeException {

	public UnregistedCommandException() {
		super();
	}
	
	public UnregistedCommandException(String message) {
		super(message);
	}
	
}
