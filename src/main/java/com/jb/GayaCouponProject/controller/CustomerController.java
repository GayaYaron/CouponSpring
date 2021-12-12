package com.jb.GayaCouponProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jb.GayaCouponProject.controller.model.LoginBody;
import com.jb.GayaCouponProject.exception.LoginFailedException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.model.Customer;
import com.jb.GayaCouponProject.service.CustomerService;
import com.jb.GayaCouponProject.service.response.LoginResponse;

@Controller
public class CustomerController extends ClientController{
	@Autowired
	private CustomerService service;

	@Override
	@PostMapping(path = "login/customer")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginBody loginBody) {
		Optional<LoginResponse> optionalLoginResponse = service.login(loginBody.getEmail(), loginBody.getPassword());
		if(optionalLoginResponse.isEmpty()) {
			throw new LoginFailedException();
		}
		return ResponseEntity.ok(optionalLoginResponse.get());
	}
	
	@PostMapping(path = "customer/coupon")
	public ResponseEntity<Void> purchaseCoupon(@RequestBody Coupon coupon) {
		service.purchaseCoupon(coupon);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "customer/coupon/all")
	public ResponseEntity<List<Coupon>> getCustomerCoupons() {
		return ResponseEntity.ok(service.getCustomerCoupons());
	}
	
	@GetMapping(path = "customer/coupon/category")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@RequestParam Integer categoryId) {
		return ResponseEntity.ok(service.getCustomerCoupons(categoryId));
	}
	
	@GetMapping(path = "customer/coupon/price")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@RequestParam double maxPrice) {
		return ResponseEntity.ok(service.getCustomerCoupons(maxPrice));
	}
	
	@GetMapping(path = "customer")
	public ResponseEntity<Customer> getCustomerDetails() {
		Optional<Customer> optionalCustomerDetails = service.getCustomerDetails();
		if(optionalCustomerDetails.isEmpty()) {
			throw new NotExistException("customer");
		}
		return ResponseEntity.ok(optionalCustomerDetails.get());
	}
	
	@GetMapping(path = "coupon/all")
	public ResponseEntity<List<Coupon>> getAllCoupons() {
		return ResponseEntity.ok(service.getAllCoupons());
	}
}
