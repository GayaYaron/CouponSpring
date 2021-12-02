package com.jb.GayaCouponProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.repository.CategoryRepository;
import com.jb.GayaCouponProject.repository.CompanyRepository;


@SpringBootApplication
public class GayaCouponProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GayaCouponProjectApplication.class, args);
		
		//initialising categories at first run
		CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
		if(categoryRepository.findAll().size()==0) {
			CategoryValue[] categoryValues = CategoryValue.values();
			for (CategoryValue categoryValue : categoryValues) {
				categoryRepository.save(new Category(categoryValue));
			}
		}
		
		CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
		if(companyRepository.findAll().size()==0) {
			companyRepository.save(new Company("company2", "company2@email.com", "pass2"));
		}
	}


}
