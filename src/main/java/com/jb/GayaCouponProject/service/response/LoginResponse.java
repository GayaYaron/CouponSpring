package com.jb.GayaCouponProject.service.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private Integer id;
	private String name;
	private String jwtToken;
	private Date expiration;
}
