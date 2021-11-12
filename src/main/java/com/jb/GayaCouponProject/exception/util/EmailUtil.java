package com.jb.GayaCouponProject.exception.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.GayaCouponProject.exception.InvalidEmailException;
import com.jb.GayaCouponProject.exception.NullVariableException;

@Component
public class EmailUtil {
	@Autowired
	private NullUtil nullUtil;
	
	/**
	 * @param email
	 * @throws InvalidEmailException - if the inserted email is not valid
	 */
	public void validate(String email) throws InvalidEmailException{
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			throw new InvalidEmailException(email);
		}
	}
	
	/**
	 * 
	 * @param email
	 * @throws InvalidEmailException - if the inserted email is not valid
	 * @throws NullVariableException - if the inserted email is null
	 */
	public void notNullAndValide(String email) throws InvalidEmailException, NullVariableException {
		nullUtil.check(email, "email");
		validate(email);
	}
}
