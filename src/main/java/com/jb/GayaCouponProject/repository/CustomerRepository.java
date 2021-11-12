package com.jb.GayaCouponProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.GayaCouponProject.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	public Optional<Customer> findByEmailAndPassword(String email, String password);
	public boolean existsByEmail(String email);
}
