package com.spring.react.login.demo.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.react.login.demo.entity.User;
import com.spring.react.login.demo.entity.UserDTO;
import com.spring.react.login.demo.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOpt = userRepo.findByUsername(username);
		
		return userOpt.orElseThrow(()-> new UsernameNotFoundException("Invalid Credentials"));
	}
	
	public UserDTO registerUser(UserDTO userDTO) {
		
		User user = this.modelMapper.map(userDTO, User.class);
		
		passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		User newUser = userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDTO.class);
		
	}
}