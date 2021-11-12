package com.jb.GayaCouponProject.exception;

public class CannotBeUpdatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CannotBeUpdatedException(String variableName) {
		super(variableName + " cannot be updated. Update canceled");
	}

}
