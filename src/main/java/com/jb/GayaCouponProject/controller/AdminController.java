package com.jb.GayaCouponProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.GayaCouponProject.controller.model.LoginBody;
import com.jb.GayaCouponProject.exception.LoginFailedException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.model.Customer;
import com.jb.GayaCouponProject.service.AdminService;
import com.jb.GayaCouponProject.service.response.LoginResponse;

@RestController
public class AdminController extends ClientController{
	@Autowired
	private AdminService service;
	
	@PostMapping(path = "login/admin")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginBody loginBody){
		Optional<LoginResponse> optionalResponse = service.login(loginBody.getEmail(), loginBody.getPassword());
		if(optionalResponse.isEmpty()) {
			throw new LoginFailedException();
		}
		return ResponseEntity.ok(optionalResponse.get());
	}
	
	@PostMapping(path = "admin/company")
	public ResponseEntity<Company> addCompany(@RequestBody Company company) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addCompany(company));
	}
	
	@PutMapping(path = "admin/company")
	public ResponseEntity<Void> updateCompany(@RequestBody Company company) {
		service.updateCompany(company);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path = "admin/company")
	public ResponseEntity<Void> deleteCompany(@RequestParam Integer companyId) {
		service.deleteCompany(companyId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "admin/company/all")
	public ResponseEntity<List<Company>> getAllCompanies() {
		return ResponseEntity.ok(service.getAllCompanies());
	}
	
	@GetMapping(path = "admin/company/one")
	public ResponseEntity<Company> getOneCompany(@RequestParam Integer companyId) {
		Optional<Company> optionalCompany = service.getOneCompany(companyId);
		if(optionalCompany.isEmpty()) {
			throw new NotExistException("company");
		}
		return ResponseEntity.ok(optionalCompany.get());
	}
	
	@PostMapping(path = "admin/customer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addCustomer(customer));
	}
	
	@PutMapping(path = "admin/customer")
	public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) {
		service.updateCustomer(customer);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path = "admin/customer")
	public ResponseEntity<Void> deleteCustomer(@RequestParam Integer customerId) {
		service.deleteCustomer(customerId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "admin/customer/all")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(service.getAllCustomers());
	}
	
	@GetMapping(path = "admin/customer/one")
	public ResponseEntity<Customer> getOneCustomer(@RequestParam Integer customerId) {
		Optional<Customer> optionalCustomer = service.getOneCustomer(customerId);
		if(optionalCustomer.isEmpty()) {
			throw new NotExistException("customer");
		}
		return ResponseEntity.ok(optionalCustomer.get());
	}
}
