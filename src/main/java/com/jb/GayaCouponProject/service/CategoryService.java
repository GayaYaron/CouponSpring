package com.jb.GayaCouponProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.repository.CategoryRepository;

public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> getAllCategories() {
		return repo.findAll();
	}
}
