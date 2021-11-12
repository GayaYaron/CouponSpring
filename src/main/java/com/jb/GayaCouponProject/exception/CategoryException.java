package com.jb.GayaCouponProject.exception;

import com.jb.GayaCouponProject.model.CategoryValue;

public class CategoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryException(CategoryValue categoryValue) {
		super(categoryValue + " was not found");
	}
}
