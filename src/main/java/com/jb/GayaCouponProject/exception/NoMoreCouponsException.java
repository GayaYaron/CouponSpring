package com.jb.GayaCouponProject.exception;

import com.jb.GayaCouponProject.model.Coupon;

public class NoMoreCouponsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoMoreCouponsException(Coupon coupon) {
		super("It seems there are no more of: '"+coupon.getTitle()+"'");
	}
	

}
