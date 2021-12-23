package com.jb.GayaCouponProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> getAllCategories() {
		return repo.findAll();
	}
}
