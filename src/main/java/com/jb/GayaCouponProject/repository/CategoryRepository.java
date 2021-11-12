package com.jb.GayaCouponProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.model.CategoryValue;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public boolean existsByValue(CategoryValue value);
	public Optional<Category> findByValue(CategoryValue value);
}
