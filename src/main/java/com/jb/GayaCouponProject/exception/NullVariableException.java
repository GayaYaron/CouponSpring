package com.jb.GayaCouponProject.exception;

public class NullVariableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullVariableException() {
		super("Something's missing, please enter all the required feilds");
	}

	public NullVariableException(String variableName) {
		super("You must enter the "+variableName);
	}
}
