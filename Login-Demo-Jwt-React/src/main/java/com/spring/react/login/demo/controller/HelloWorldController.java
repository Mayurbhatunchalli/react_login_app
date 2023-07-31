package com.spring.react.login.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/api/auth")
public class HelloWorldController {

	@GetMapping("/login")
	public String hello() {
		return "Hello World...!!!";
	}
	
	

}
