package com.jb.GayaCouponProject.service.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.GayaCouponProject.exception.CategoryException;
import com.jb.GayaCouponProject.exception.util.NullUtil;
import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.repository.CategoryRepository;


@Component
public class CategoryUtil {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private NullUtil nullUtil;
	
	public Category getCategoryFromValue(CategoryValue categoryValue) {
		nullUtil.check(categoryValue, "category value");
		Optional<Category> optionalCategory = categoryRepository.findByValue(categoryValue);
		if(optionalCategory.isEmpty()) {
			throw new CategoryException(categoryValue);
		}
		return optionalCategory.get();
	}
}
