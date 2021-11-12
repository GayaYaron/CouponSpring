package com.jb.GayaCouponProject.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetail {
	private int code;
	private String message;
}
