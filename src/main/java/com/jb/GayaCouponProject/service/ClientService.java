package com.jb.GayaCouponProject.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.jb.GayaCouponProject.repository.CompanyRepository;
import com.jb.GayaCouponProject.repository.CouponRepository;
import com.jb.GayaCouponProject.repository.CustomerRepository;
import com.jb.GayaCouponProject.repository.PurchaseRepository;
import com.jb.GayaCouponProject.service.response.LoginResponse;
import com.jb.GayaCouponProject.service.util.CategoryUtil;


public abstract class ClientService {
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected PurchaseRepository purchaseRepository;
	@Autowired
	protected CategoryUtil categoryUtil;
	
	public abstract Optional<LoginResponse> login(String email, String password);
}
