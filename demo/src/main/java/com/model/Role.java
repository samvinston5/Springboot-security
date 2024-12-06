package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="role_table")
public class Role {
	
	@Id
	@GeneratedValue
	private int id;

	String roleName;
	
	@ManyToOne
	User user;
	
	public Role() {
		
	}

	public Role(String roleName, User u) {
		this.roleName = roleName;
		this.user = u;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
