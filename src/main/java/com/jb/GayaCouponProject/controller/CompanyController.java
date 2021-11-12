package com.jb.GayaCouponProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jb.GayaCouponProject.controller.model.LoginBody;
import com.jb.GayaCouponProject.exception.LoginFailedException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.service.CompanyService;
import com.jb.GayaCouponProject.service.response.LoginResponse;

@Controller
public class CompanyController extends ClientController{
	@Autowired
	private CompanyService service;
	
	@GetMapping(path = "login/company")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginBody loginBody) {
		Optional<LoginResponse> optionalLoginResponse = service.login(loginBody.getEmail(), loginBody.getPassword());
		if(optionalLoginResponse.isEmpty()) {
			throw new LoginFailedException();
		}
		return ResponseEntity.ok(optionalLoginResponse.get());
	}
	
	@PostMapping(path = "company/coupon")
	public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addCoupon(coupon));
	}
	
	@PutMapping(path = "company/coupon")
	public ResponseEntity<Void> updateCoupon(@RequestBody Coupon coupon) {
		service.updateCoupon(coupon);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path = "company/coupon")
	public ResponseEntity<Void> deleteCoupon(@RequestParam Integer couponId) {
		service.deleteCoupon(couponId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "company/coupon/all")
	public ResponseEntity<List<Coupon>> getCompanyCoupons() {
		return ResponseEntity.ok(service.getCompanyCoupons());
	}
	
	@GetMapping(path = "company/coupon/category")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@RequestParam CategoryValue categoryValue) {
		return ResponseEntity.ok(service.getCompanyCoupons(categoryValue));
	}
	
	@GetMapping(path = "company/coupon/price")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@RequestParam double maxPrice) {
		return ResponseEntity.ok(service.getCompanyCoupons(maxPrice));
	}
	
	@GetMapping(path = "company")
	public ResponseEntity<Company> getCompanyDetails() {
		Optional<Company> optionalCompanyDetails = service.getCompanyDetails();
		if(optionalCompanyDetails.isEmpty()) {
			throw new NotExistException("company");
		}
		return ResponseEntity.ok(optionalCompanyDetails.get());
	}
}
