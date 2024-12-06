package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.DAO.UserDao;

public class CustomUserDetailService  implements UserDetailsService{

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.model.User user = userDao.findById(username).get();
		
		List<SimpleGrantedAuthority> authorities = user.getRoleList().stream()
				.map(role-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
		
		org.springframework.security.core.userdetails.User dbUser = new org.springframework.security.core.userdetails.User(user.getUserId(),user.getPassword(),authorities);
		
		
		
		
		return dbUser;
	}
	

}
