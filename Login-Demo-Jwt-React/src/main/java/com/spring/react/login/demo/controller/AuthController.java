package com.spring.react.login.demo.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.react.login.demo.entity.RefreshToken;
import com.spring.react.login.demo.entity.UserDTO;
import com.spring.react.login.demo.exception.TokenRefreshException;
import com.spring.react.login.demo.request.JwtRequest;
import com.spring.react.login.demo.request.TokenRefreshRequest;
import com.spring.react.login.demo.response.JwtResponse;
import com.spring.react.login.demo.response.TokenRefreshResponse;
import com.spring.react.login.demo.security.jwt.JwtUtils;
import com.spring.react.login.demo.service.JwtUserDetailsService;
import com.spring.react.login.demo.service.RefreshTokenService;


@RestController
@CrossOrigin(origins="*", exposedHeaders = "**")
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtUserDetailsService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:3000",methods = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		JwtResponse jwtResponse = new JwtResponse();
		
		jwtResponse.setJwttoken("Bearer " + token);

		return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK); 
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
		
		String requestRefreshToken = request.getRefreshToken();
		
		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verificationToken)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtTokenUtil.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token,requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database"));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:3000",methods = RequestMethod.POST)
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user) {
		
		UserDTO newUser = this.userService.registerUser(user);
		
		return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);
	}
	
}
