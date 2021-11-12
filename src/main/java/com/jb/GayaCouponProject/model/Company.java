package com.jb.GayaCouponProject.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	@Column(nullable = false)
	private String name;
	@NotNull
	@Column(nullable = false)
	private String email;
	@NotNull
	@Column(nullable = false)
	private String password;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "company")
	private List<Coupon> coupons;
	
	public Company(String name, String email, String password) {
		super();
		setEmail(email);
		this.name = name;
		this.password = password;
	}

	public Company(Integer id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
