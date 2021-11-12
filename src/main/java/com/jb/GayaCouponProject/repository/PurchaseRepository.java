package com.jb.GayaCouponProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.GayaCouponProject.model.Purchase;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	public List<Purchase> findByCustomerId(Integer customerId);

	public List<Purchase> findByCouponId(Integer couponId);

	public Long deleteByCustomerId(Integer customerId);

	public Long deleteByCouponId(Integer couponId);

	public boolean existsByCustomerIdAndCouponId(Integer customerId, Integer couponId);
}
