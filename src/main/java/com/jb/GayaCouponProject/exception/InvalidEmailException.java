package com.jb.GayaCouponProject.exception;

public class InvalidEmailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String email) {
		super(email+" is an invalid email, please enter a valid email");
	}

}
