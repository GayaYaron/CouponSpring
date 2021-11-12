package com.jb.GayaCouponProject.exception;

public class LoginFailedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
		super("Email or password is incorrect");
	}
}
