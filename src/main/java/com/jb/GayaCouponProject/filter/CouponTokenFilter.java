package com.jb.GayaCouponProject.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jb.GayaCouponProject.service.util.ClientDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class CouponTokenFilter implements Filter, ApplicationContextAware {
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String method = httpRequest.getMethod();
		if (method.equals("OPTIONS")) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			String token = httpRequest.getHeader("Authorization");
			try {
				token = token.substring(7);
				Claims claims = Jwts.parserBuilder()
						.setSigningKey("dfjbv87yfni4rht8hvfhb8r7eyehrdljfcnvbefjhisfhuisfuehghbgruonjv".getBytes())
						.build().parseClaimsJws(token).getBody();

				ClientDetail clientDetail = context.getBean(ClientDetail.class);
				clientDetail.setId(Integer.parseInt(claims.getSubject()));
				clientDetail.setName(claims.get("clientName").toString());

			} catch (Exception e) {
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			chain.doFilter(httpRequest, httpResponse);
		}
	}
}
