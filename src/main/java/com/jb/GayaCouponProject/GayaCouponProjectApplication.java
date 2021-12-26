package com.jb.GayaCouponProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jb.GayaCouponProject.dailyjob.CouponExpirationDailyJob;
import com.jb.GayaCouponProject.model.Category;
import com.jb.GayaCouponProject.model.CategoryValue;
import com.jb.GayaCouponProject.model.Company;
import com.jb.GayaCouponProject.model.Customer;
import com.jb.GayaCouponProject.repository.CategoryRepository;
import com.jb.GayaCouponProject.service.AdminService;

@SpringBootApplication
public class GayaCouponProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GayaCouponProjectApplication.class, args);

		// initialising categories at first run
		CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
		if (categoryRepository.findAll().size() == 0) {
			CategoryValue[] categoryValues = CategoryValue.values();
			for (CategoryValue categoryValue : categoryValues) {
				categoryRepository.save(new Category(categoryValue));
			}
		}

		// starting the daily job
		CouponExpirationDailyJob couponExpirationDailyJob = context.getBean(CouponExpirationDailyJob.class);
		Thread couponExpirationJobThread = new Thread(couponExpirationDailyJob);
		couponExpirationJobThread.setDaemon(true);
		couponExpirationJobThread.start();

		exampleData(context);
	}

	public static void exampleData(ConfigurableApplicationContext context) {
		AdminService adminService = context.getBean(AdminService.class);

		if(adminService.getAllCompanies().size() == 0) {
			adminService.addCompany(new Company("company2", "company2@email.com", "pass2"));
		}
		
		if(adminService.getAllCustomers().size() == 0) {
			adminService.addCustomer(new Customer("custo", "mer", "customer@email.com", "cus2"));
		}
		
	}
}
