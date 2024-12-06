package com.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtToken {
	
	private String token;
	private static final String SECRETKEY="##XXXAACCCRR#123###AAA";

	public void generateToken(String userId,String password,String role) {
		this.token = Jwts.builder()
				.claim("userid", userId)
				.claim("password", password)
				.claim("role", role)
				.signWith(SignatureAlgorithm.HS256, SECRETKEY)
				.compact();
	}
	
	public String getToken() {
		return token;
	}
	
	public boolean validate(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
			return true;
			
		}
		catch(Exception e) {
			return false;
		}
	}
}
