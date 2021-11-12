package com.jb.GayaCouponProject.exception;

import com.jb.GayaCouponProject.model.Coupon;

public class CouponExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponExpiredException(Coupon coupon) {
		super("'"+ coupon.getTitle() + "' expired, purchase cancled");
	}

}
