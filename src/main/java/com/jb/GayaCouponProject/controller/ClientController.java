package com.jb.GayaCouponProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.jb.GayaCouponProject.controller.model.LoginBody;
import com.jb.GayaCouponProject.service.response.LoginResponse;

public abstract class ClientController {
	public abstract ResponseEntity<LoginResponse> login(@RequestBody LoginBody loginBody);
}
