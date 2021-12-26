package com.jb.GayaCouponProject.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	@Column(nullable = false)
	private String title;
	private String description;
	private LocalDate startDate;
	@NotNull
	@Column(nullable = false)
	private LocalDate endDate;
	@NotNull
	@Column(nullable = false)
	private int amount;
	private double price;
	@Column(length = 100000)
	private String image;
	@ManyToOne
	private Category category;
	@ManyToOne
	private Company company;
	
	public Coupon(String title, LocalDate endDate, int amount, Category category) {
		super();
		this.title = title;
		this.endDate = endDate;
		this.amount = amount;
		this.category = category;
	}

	public Coupon(String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price,
			String image, Category category) {
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.category = category;
	}
}
