package com.jb.GayaCouponProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.GayaCouponProject.model.Company;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	public Optional<Company> findByEmailAndPassword(String email, String password);
	public boolean existsByName(String name);
	public boolean existsByEmail(String email);
}
