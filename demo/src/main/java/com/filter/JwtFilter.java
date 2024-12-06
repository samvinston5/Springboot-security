package com.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
	 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JwtToken tg = new JwtToken();
		 response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, Origin, User-Agent, DNT, Cache-Control, X-Mx-ReqToken, Keep-Alive, X-Requested-With, If-Modified-Since");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
		if(request.getRequestURI().equals("/api/auth") && request.getMethod().equals("POST"))
		{
			System.out.println("filter invoked");
			filterChain.doFilter(request, response);
			return;
		}
		else {
			String authdata=request.getHeader("Authorization");
			if(authdata!=null && authdata.startsWith("Bearer "))
			{
				String original_token=authdata.substring(7);
				 System.out.println(original_token);
				 try {
					 if(tg.validate(original_token))
					 {
						 System.out.println("validated");
						 Claims claims = Jwts.parser().setSigningKey(tg.getSecretkey()).parseClaimsJws(original_token).getBody();
	                     String role = claims.get("role", String.class);
						 SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(role, null, Collections.singleton(new SimpleGrantedAuthority(role))));
						 filterChain.doFilter(request, response);
					 }
				 }catch(Exception e)
				 {
					 System.out.println("invalid token details");
					
					 System.out.println(e);
					 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			            response.setContentType("application/json");
			            response.getWriter().write("{\"error\": \"Invalid token\"}");
					return;
					
				 }
			}
		}
		
	}
}