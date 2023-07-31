package com.spring.react.login.demo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomerPasswordEncoder {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public CustomerPasswordEncoder() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

}
