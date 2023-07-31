package com.login.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.login.demo.entity.User;
import com.login.demo.web.dto.UserRegistrationDTO;

public interface UserService extends UserDetailsService{
	
	User save(UserRegistrationDTO registrationDTO);

}
