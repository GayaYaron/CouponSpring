package com.jb.GayaCouponProject.service.response;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jb.GayaCouponProject.exception.util.NullUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Component
public class LoginResponseMaker {
	@Autowired
	private NullUtil nullUtil;

	public LoginResponse make(Integer id, String name) {
		nullUtil.check(id, "clientId");
		if (name == null) {
			name = "";
		}
		Date expitarion = new Date(System.currentTimeMillis() + 1800000);
		String jwts = Jwts.builder()
				.setIssuer("CouponsWebsite")
				.setSubject("" + id)
				.claim("clientName", name)
				.setIssuedAt(new Date())
				.setExpiration(expitarion)
				.signWith(Keys.hmacShaKeyFor("dfjbv87yfni4rht8hvfhb8r7eyehrdljfcnvbefjhisfhuisfuehghbgruonjv".getBytes()))
				.compact();
		return new LoginResponse(id, name, jwts, expitarion);
	}

}
