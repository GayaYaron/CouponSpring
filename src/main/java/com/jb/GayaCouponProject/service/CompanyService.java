package com.jb.GayaCouponProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jb.GayaCouponProject.exception.AlreadyExistsException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.exception.util.NullUtil;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.service.response.LoginResponse;
import com.jb.GayaCouponProject.service.response.LoginResponseMaker;
import com.jb.GayaCouponProject.service.util.ClientDetail;
import com.jb.GayaCouponProject.service.util.Lock;


@Service
@Transactional
public class CompanyService extends ClientService {
	@Autowired
	private ClientDetail clientDetail;
	@Autowired
	private LoginResponseMaker responseMaker;
	@Autowired
	private NullUtil nullUtil;

	public Optional<LoginResponse> login(String email, String password) {
		nullUtil.check(email, "company email");
		nullUtil.check(password, "company password");
		Optional<Company> companyOptional = companyRepository.findByEmailAndPassword(email, password);
		if(companyOptional.isEmpty()) {
			return Optional.empty();
		}
		Company company = companyOptional.get();
		return Optional.of(responseMaker.make(company.getId(), company.getName()));
	}

	/**
	 * sets the coupon's company to this company and adds the coupon to the database
	 * if the company does not already have a coupon with this title
	 * 
	 * @param coupon
	 * @return optional of the saved coupon with generated id
	 * @throws AlreadyExistsException - if the company has an existing coupon with
	 *                                the same title
	 */
	@Transactional(readOnly = false)
	public Coupon addCoupon(Coupon coupon) {
		nullUtil.check(coupon, "coupon to add");
		synchronized (Lock.coupon()) {
			if (couponRepository.existsByCompanyIdAndTitle(clientDetail.getId(), coupon.getTitle())) {
				throw new AlreadyExistsException("coupon", "title", coupon.getTitle());
			}
			if(coupon.getCompany()==null || !coupon.getCompany().getId().equals(clientDetail.getId())) {
				coupon.setCompany(getCompanyDetails().get());
			}
			synchronized (Lock.coupon()) {
				return couponRepository.save(coupon);
			}
		}
	}

	/**
	 * sets the coupon's company to this company and updates the coupon in the
	 * database with same coupon id to the values of given id
	 * 
	 * @param coupon
	 * @throws NotExistException           - if there is no coupon in the database
	 *                                     with such id
	 * @throws MismatchingCompanyException - if the company of the coupon in the
	 *                                     database is not the same as the service's
	 *                                     company
	 */
	@Transactional(readOnly = false)
	public void updateCoupon(Coupon coupon) {
		if (coupon != null) {
			nullUtil.check(coupon.getId(), "coupon id");
			Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
			if (optionalCoupon.isEmpty()) {
				throw new NotExistException("coupon");
			}
			Coupon databaseCoupon = optionalCoupon.get();
			if (!databaseCoupon.getCompany().getId().equals(clientDetail.getId())) {
				throw new NotExistException("coupon");
			}
			synchronized (Lock.coupon()) {
				couponRepository.save(coupon);
			}
		}
	}

	/**
	 * deletes the coupon and all its purchases, as long as it exists and belongs to
	 * the service's company
	 * 
	 * @param couponId
	 * @throws NotExistException           - if there is no coupon with such id
	 * @throws MismatchingCompanyException - if the coupon's company does not match
	 *                                     the service's
	 */
	@Transactional(readOnly = false)
	public void deleteCoupon(Integer couponId) {
		if (couponId != null) {
			Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
			if (optionalCoupon.isEmpty()) {
				throw new NotExistException("coupon with id " + couponId);
			}
			Coupon coupon = optionalCoupon.get();
			if (!coupon.getCompany().getId().equals(clientDetail.getId())) {
				throw new NotExistException("coupon with id " + couponId);
			}
			purchaseRepository.deleteByCouponId(coupon.getId());
			couponRepository.delete(coupon);
		}
	}

	/**
	 * 
	 * @return all the coupons of the service's company
	 */
	public List<Coupon> getCompanyCoupons() {
		return couponRepository.findByCompanyId(clientDetail.getId());
	}

	/**
	 * 
	 * @param category
	 * @return all the coupons of the service's company that are of given category
	 */
	public List<Coupon> getCompanyCoupons(CategoryValue categoryValue) {
		if(categoryValue == null) {
			return getCompanyCoupons();
		}
		return couponRepository.findByCompanyIdAndCategoryId(clientDetail.getId(),
				categoryUtil.getCategoryFromValue(categoryValue).getId());
	}

	/**
	 * 
	 * @param maxPrice
	 * @return all the coupons of the service's company that have a price lower or
	 *         equal to the given macPrice
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepository.findByCompanyIdAndPriceLessThanEqual(clientDetail.getId(), maxPrice);
	}

	/**
	 * 
	 * @return optional of the company that logged in
	 */
	public Optional<Company> getCompanyDetails() {
		return companyRepository.findById(clientDetail.getId());
	}
}
