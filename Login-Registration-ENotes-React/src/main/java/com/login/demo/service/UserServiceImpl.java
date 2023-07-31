package com.login.demo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.demo.entity.Role;
import com.login.demo.entity.User;
import com.login.demo.repository.UserRepository;
import com.login.demo.web.dto.UserRegistrationDTO;

@Service
public class UserServiceImpl implements UserService {
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	private UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepository) {
		
		super();
		this.userRepo = userRepository;
		
	}
	

	@Override
	public User save(UserRegistrationDTO registrationDTO) {
		
		User user = new User(registrationDTO.getFirstName(), registrationDTO.getLastName(), 
				registrationDTO.getEmail(),registrationDTO.getPassword(), Arrays.asList(new Role("ROLE_USER")));
		
		return userRepo.save(user);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username);
		
		if(user == null) {
			
			System.out.println("User name not found");
			throw new UsernameNotFoundException("User name not found");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
	}
	
}
