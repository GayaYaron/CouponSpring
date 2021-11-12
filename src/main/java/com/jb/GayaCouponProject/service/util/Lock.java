package com.jb.GayaCouponProject.service.util;

public class Lock {
	private static Lock coupon = new Lock();
	private static Lock company = new Lock();
	private static Lock customer = new Lock();
	
	private Lock() {
		
	}
	
	public static Lock coupon() {
		return coupon;
	}
	public static Lock company() {
		return company;
	}
	public static Lock customer() {
		return customer;
	}
}
