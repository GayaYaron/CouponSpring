package com.jb.GayaCouponProject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Coupon coupon;
	
	public Purchase(Customer customer, Coupon coupon) {
		super();
		this.customer = customer;
		this.coupon = coupon;
	}
}
