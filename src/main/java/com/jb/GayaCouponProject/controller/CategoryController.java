package com.jb.GayaCouponProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.repository.CategoryRepository;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepository repo;
	
	@GetMapping(path = "category/all")
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(repo.findAll());
	}

}
