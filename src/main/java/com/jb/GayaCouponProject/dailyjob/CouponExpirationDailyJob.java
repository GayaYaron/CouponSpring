package com.jb.GayaCouponProject.dailyjob;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jb.GayaCouponProject.exception.JobGoWrongException;
import com.jb.GayaCouponProject.model.Coupon;
import com.jb.GayaCouponProject.repository.CouponRepository;
import com.jb.GayaCouponProject.repository.PurchaseRepository;


@Component
@Transactional
public class CouponExpirationDailyJob implements Runnable{
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	private boolean quit = false;
	private final int day = 86400000;

	@Transactional(readOnly = false)
	@Override
	public void run() {
		while(!quit) {
			List<Coupon> expiredCoupons = couponRepository.findByEndDateBefore(LocalDate.now());
			for (Coupon coupon : expiredCoupons) {
				purchaseRepository.deleteByCouponId(coupon.getId());
				couponRepository.delete(coupon);
			}
			try {
				Thread.sleep(day);
			} catch (InterruptedException e) {
				throw new JobGoWrongException("something interrupted the daily job");
			}
		}
	}
	
	public void stop() {
		quit = true;
	}
}
