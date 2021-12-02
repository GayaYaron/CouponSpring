package com.jb.GayaCouponProject.exception;

public class AlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String objectName, String existingVariableName, String existingVariable) {
		super(objectName+" with "+existingVariableName+": '"+existingVariable+"' already exists");
	}
}
