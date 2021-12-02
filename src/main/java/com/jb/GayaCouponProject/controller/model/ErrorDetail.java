package com.jb.GayaCouponProject.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class ErrorDetail {
	@ToString.Include
	private static final String FROM = "coupon app";
	private int couponSystemCode;
	private String message;
}
