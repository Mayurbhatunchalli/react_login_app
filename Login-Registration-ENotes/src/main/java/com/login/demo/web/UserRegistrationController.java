package com.login.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.login.demo.service.UserService;
import com.login.demo.web.dto.UserRegistrationDTO;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDTO userRegistrationDTO() {
		
		return new UserRegistrationDTO();
	}
	
	@GetMapping
	public String showRegistrationForm() {
				
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDTO registrationDTO) {
		
		userService.save(registrationDTO);
		
		return "redirect:/register?success";
		
	}
}
