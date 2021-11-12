package com.jb.GayaCouponProject.filter;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponTokenFilterConfig {
	
	@Bean
	public FilterRegistrationBean<CouponTokenFilter> createTokenFilter() {
		FilterRegistrationBean<CouponTokenFilter> registrationBean = new FilterRegistrationBean<CouponTokenFilter>();
		registrationBean.setFilter(couponTokenFilter());
		registrationBean.addUrlPatterns("/admin/*", "/company/*", "/customer/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
	@Bean
	public CouponTokenFilter couponTokenFilter() {
		return new CouponTokenFilter();
	}
	
}
