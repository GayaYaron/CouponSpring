package com.jb.GayaCouponProject.exception;

import com.jb.GayaCouponProject.model.Coupon;

public class CouponAlreadyPurchasedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponAlreadyPurchasedException(Coupon coupon, String customerName) {
		super(customerName+", you already purchased this coupon- '"+coupon.getTitle()+"'. You cannot buy the same coupon twice.");
	}

}
