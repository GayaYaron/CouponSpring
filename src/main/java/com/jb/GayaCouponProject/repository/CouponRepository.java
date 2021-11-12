package com.jb.GayaCouponProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.GayaCouponProject.model.Coupon;


@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>{
	public List<Coupon> findByCompanyId(Integer companyId);
	public List<Coupon> findByCompanyIdAndCategoryId(Integer companyId, Integer categoryId);
	public boolean existsByCompanyIdAndTitle(Integer companyId, String title);
	public List<Coupon> findByEndDateBefore(LocalDate date);
	public List<Coupon> findByCompanyIdAndPriceLessThanEqual(Integer companyId, double maxPrice);
	
}
