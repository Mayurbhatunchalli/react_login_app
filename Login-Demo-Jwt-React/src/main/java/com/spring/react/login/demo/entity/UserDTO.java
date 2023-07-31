package com.spring.react.login.demo.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
	
	
	private Long id;
	
	private String username;
	private String email;
	private String password;
	private String about;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String username, String email, String password, String about) {
		
		this.username = username;
		this.email = email;
		this.password = password;
		this.about = about;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}
