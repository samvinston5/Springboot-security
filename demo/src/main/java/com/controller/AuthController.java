package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.DAO.RoleDao;
import com.DAO.UserDao;
import com.jwt.JwtToken;
import com.model.*;

@RestController
public class AuthController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@PostMapping("/register")
	public ResponseEntity<?> regUser(@RequestBody User user) {
		
		List<Role> roleList = new  ArrayList<>();
		
		Role role = new Role();
		role.setRoleName("USER");
		role.setUser(user);
		
		user.setRoleList(roleList);
		userDao.save(user);
		roleDao.save(role);
		
		JwtToken tokenGen = new JwtToken();
		tokenGen.generateToken(user.getUserId(), user.getPassword(), "USER");
		
		String token = tokenGen.getToken();
		return new ResponseEntity<>(token,HttpStatus.OK);
		
	}
	
}
