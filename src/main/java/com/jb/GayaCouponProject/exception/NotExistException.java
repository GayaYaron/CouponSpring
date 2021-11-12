package com.jb.GayaCouponProject.exception;

public class NotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistException(String searchedType) {
		super("The " + searchedType + " does not exist");
	}
}
