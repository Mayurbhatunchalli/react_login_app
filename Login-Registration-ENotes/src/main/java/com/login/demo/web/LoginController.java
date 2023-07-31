package com.login.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@GetMapping("/profile")
	public String loginSuccess() {
		
		return "user/profile";
	}
	
	@GetMapping("/addNotes")
	public String addNotes() {
		
		return "user/add_notes";
	}
	
	@GetMapping("/viewNotes")
	public String viewNotes() {
		
		return "user/view_notes";
	}
	
	@GetMapping("/editNotes")
	public String editNotes() {
		
		return "user/edit_notes";
	}
	

}
