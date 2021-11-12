package com.jb.GayaCouponProject.exception.util;

public enum ExceptionCode {
	ALREADY_EXIST(1), CANNOT_BE_UPDATED(2), CATEGORY(3), COUPON_ALREADY_PURCHASED(4), COUPON_EXPIRED(5),
	INVALID_EMAIL(6), JOB_GO_WRONG(7), LOGIN_FAILED(8), MISMATCHING_COMPANY(9), NO_MORE_COUPONS(10), NOT_EXIST(11),
	NULL_VARIABLE(12);

	private int code;

	private ExceptionCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
