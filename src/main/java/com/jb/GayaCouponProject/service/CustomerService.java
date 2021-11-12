package com.jb.GayaCouponProject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jb.GayaCouponProject.exception.CouponAlreadyPurchasedException;
import com.jb.GayaCouponProject.exception.CouponExpiredException;
import com.jb.GayaCouponProject.exception.NoMoreCouponsException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.exception.util.NullUtil;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.model.Customer;
import com.jb.GayaCouponProject.model.Purchase;
import com.jb.GayaCouponProject.service.response.LoginResponse;
import com.jb.GayaCouponProject.service.response.LoginResponseMaker;
import com.jb.GayaCouponProject.service.util.ClientDetail;
import com.jb.GayaCouponProject.service.util.Lock;

@Service
@Transactional
public class CustomerService extends ClientService {
	@Autowired
	private ClientDetail customerDetail;
	@Autowired
	private LoginResponseMaker responseMaker;
	@Autowired
	private NullUtil nullUtil;
	
	public Optional<LoginResponse> login(String email, String password) {
		nullUtil.check(email, "customer email");
		nullUtil.check(password, "customer password");
		Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword(email, password);
		if(optionalCustomer.isEmpty()) {
			return Optional.empty();
		}
		Customer customer = optionalCustomer.get();
		return Optional.of(responseMaker.make(customer.getId(), customer.getFirstName()));
	}

	/**
	 * adds the coupon purchase and reduces the amount left for this coupon by 1
	 * 
	 * @param coupon
	 * @throws CouponAlreadyPurchasedException - if this customer already bought
	 *                                         this coupon before
	 * @throws NotExistException               - if such coupon does not exist in
	 *                                         the database
	 * @throws NoMoreCouponsException          - if the amount left for this coupon
	 *                                         is lower or equal to 0
	 * @throws CouponExpiredException          - if the expiration date has arrived
	 *                                         or passed
	 */
	@Transactional(readOnly = false)
	public void purchaseCoupon(Coupon coupon) {
		if (coupon != null) {
			if (purchaseRepository.existsByCustomerIdAndCouponId(customerDetail.getId(), coupon.getId())) {
				throw new CouponAlreadyPurchasedException(coupon, customerDetail.getName());
			}

			Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
			if (optionalCoupon.isEmpty()) {
				throw new NotExistException("coupon");
			}
			Coupon databaseCoupon = optionalCoupon.get();
			if (databaseCoupon.getAmount() <= 0) {
				throw new NoMoreCouponsException(databaseCoupon);
			}
			if (databaseCoupon.getEndDate().compareTo(LocalDate.now()) <= 0) {
				throw new CouponExpiredException(databaseCoupon);
			}
			synchronized (Lock.coupon()) {
				databaseCoupon.setAmount(databaseCoupon.getAmount() - 1);
				purchaseRepository.save(new Purchase(getCustomerDetails().get(), databaseCoupon));
				couponRepository.save(databaseCoupon);
			}
		}
	}

	/**
	 * 
	 * @return a list of all coupons purchased by this customer
	 */
	public List<Coupon> getCustomerCoupons() {
		List<Purchase> purchases = purchaseRepository.findByCustomerId(customerDetail.getId());
		List<Coupon> coupons = new ArrayList<Coupon>(purchases.size());
		for (Purchase purchase : purchases) {
			coupons.add(purchase.getCoupon());
		}
		return coupons;
	}

	/**
	 * 
	 * @param category
	 * @return a list of coupons purchased by the customer that logged in and are of
	 *         the entered category
	 */
	public List<Coupon> getCustomerCoupons(CategoryValue categoryValue) {
		if(categoryValue == null) {
			return getCustomerCoupons();
		}
		return getCustomerCoupons().stream()
				.filter(coupon -> coupon.getCategory().equals(categoryUtil.getCategoryFromValue(categoryValue)))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param maxPrice
	 * @return a list of coupons purchased by the customer that logged in and have a
	 *         price lower or equal to the given maxPrice
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		return getCustomerCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return an optional of the customer that logged in
	 */
	public Optional<Customer> getCustomerDetails() {
		return customerRepository.findById(customerDetail.getId());
	}
	
	/**
	 * 
	 * @return all coupons in the database
	 */
	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}
}
