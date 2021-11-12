package com.jb.GayaCouponProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jb.GayaCouponProject.exception.AlreadyExistsException;
import com.jb.GayaCouponProject.exception.CannotBeUpdatedException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.exception.util.EmailUtil;
import com.jb.GayaCouponProject.exception.util.NullUtil;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.model.Customer;
import com.jb.GayaCouponProject.service.response.LoginResponse;
import com.jb.GayaCouponProject.service.response.LoginResponseMaker;
import com.jb.GayaCouponProject.service.util.Lock;


@Service
@Transactional
public class AdminService extends ClientService {
	@Autowired
	private LoginResponseMaker responseMaker;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private NullUtil nullUtil;
	
	/**
	 * checks if the email and password are the correct ones
	 * 
	 * @param email
	 * @param password
	 * @return true - if email and password are correct, false otherwise
	 */
	public Optional<LoginResponse> login(String email, String password) {
		nullUtil.check(email, "admin email");
		nullUtil.check(password, "admin password");
		if(email.equals("admin@admin.com") && password.equals("admin")) {
			return Optional.of(responseMaker.make(0, "Admin"));
		}	
		return Optional.empty();
	}

	/**
	 * checks if the company's name and email don't exist in the database, if so-
	 * adds the company
	 * 
	 * @param company
	 * @return optional of the saved company with generated id
	 * @throws AlreadyExistsException - if name or email already exist in the
	 *                                database
	 */
	@Transactional(readOnly = false)
	public Company addCompany(Company company) {
		nullUtil.check(company, "company to add");
		emailUtil.notNullAndValide(company.getEmail());
		synchronized (Lock.company()) {
			if (companyRepository.existsByName(company.getName())) {
				throw new AlreadyExistsException("company", "name", company.getName());
			}
			if (companyRepository.existsByEmail(company.getEmail())) {
				throw new AlreadyExistsException("company", "email", company.getEmail());
			}
			return companyRepository.save(company);
		}

	}

	/**
	 * updates the company in the database with same id as the given company to
	 * match the values of the given company, as long as the name is the same
	 * 
	 * @param company
	 * @throws NotExistException        - if there is no company in the database
	 *                                  with the id of the given company
	 * @throws CannotBeUpdatedException - if the given company's name doesn't match
	 *                                  the one in the database
	 */
	@Transactional(readOnly = false)
	public void updateCompany(Company company) {
		if (company != null) {
			emailUtil.notNullAndValide(company.getEmail());
			nullUtil.check(company.getId(), "company id");
			Optional<Company> optionalPreviousCompany = companyRepository.findById(company.getId());
			if (optionalPreviousCompany.isEmpty()) {
				throw new NotExistException("company");
			}
			if (!optionalPreviousCompany.get().getName().equals(company.getName())) {
				throw new CannotBeUpdatedException("company name");
			}
			synchronized (Lock.company()) {
				companyRepository.save(company);
			}
		}
	}

	/**
	 * deletes the company if it exists in the database and all coupons made by the
	 * company and their purchases
	 * 
	 * @param companyId
	 * @throws NotExistException - if the given id does not belong to a company in
	 *                           the database
	 */
	@Transactional(readOnly = false)
	public void deleteCompany(Integer companyId) {
		if (companyId != null) {
			Optional<Company> optionalCompany = companyRepository.findById(companyId);
			if (optionalCompany.isEmpty()) {
				throw new NotExistException("company with id " + companyId);
			}
			Company toDeleteCompany = optionalCompany.get();
			List<Coupon> companyCoupons = couponRepository.findByCompanyId(toDeleteCompany.getId());
			for (Coupon coupon : companyCoupons) {
				purchaseRepository.deleteByCouponId(coupon.getId());
				couponRepository.delete(coupon);
			}
			companyRepository.delete(toDeleteCompany);
		}
	}

	/**
	 * 
	 * @return List<Company> all the companies in the database
	 */
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	/**
	 * 
	 * @param companyId
	 * @return optional of company if it exists, otherwise an empty optional
	 */
	public Optional<Company> getOneCompany(Integer companyId) {
		if (companyId == null) {
			return Optional.empty();
		}
		return companyRepository.findById(companyId);
	}

	/**
	 * adds the customer to the database if there is no other customer with the same
	 * email
	 * 
	 * @param customer
	 * @return optional of saved customer with generated id
	 * @throws AlreadyExistsException - if there is customer in the database with
	 *                                the same email as the entered customer
	 */
	@Transactional(readOnly = false)
	public Customer addCustomer(Customer customer) {
		nullUtil.check(customer, "customer to add");
		emailUtil.notNullAndValide(customer.getEmail());
		synchronized (Lock.customer()) {
			if (customerRepository.existsByEmail(customer.getEmail())) {
				throw new AlreadyExistsException("customer", "email", customer.getEmail());
			}
			return customerRepository.save(customer);
		}
	}

	/**
	 * updates the customer in the database with same customerId to the values of
	 * given customer
	 * 
	 * @param customer
	 * @exception NotExistException- if customer does not exist in the database
	 */
	@Transactional(readOnly = false)
	public void updateCustomer(Customer customer) {
		if (customer != null) {
			emailUtil.notNullAndValide(customer.getEmail());
			nullUtil.check(customer.getId(), "customer id");
			Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
			if (optionalCustomer.isEmpty()) {
				throw new NotExistException("customer");
			}
			synchronized (Lock.customer()) {
				customerRepository.save(customer);
			}
		}
	}

	/**
	 * deletes the customer and all the customer's purchases
	 * 
	 * @param customerId
	 * 
	 * @throws NotExistsException - if there is no customer in the database with the
	 *                            given id
	 */
	@Transactional(readOnly = false)
	public void deleteCustomer(Integer customerId) {
		if (customerId != null) {
			Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
			if (optionalCustomer.isEmpty()) {
				throw new NotExistException("customer with id " + customerId);
			}
			purchaseRepository.deleteByCustomerId(optionalCustomer.get().getId());
			customerRepository.deleteById(customerId);
		}
	}

	/**
	 * 
	 * @return List<Customer> - all the customers in the database
	 */
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 * 
	 * @param customerId
	 * @return an optional of the customer with given id if one exists in the
	 *         database, otherwise- an empty optional
	 */
	public Optional<Customer> getOneCustomer(Integer customerId) {
		if (customerId == null) {
			return Optional.empty();
		}
		return customerRepository.findById(customerId);
	}
}
