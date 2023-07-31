package com.spring.react.login.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.react.login.demo.security.jwt.AuthEntryPointJwt;
import com.spring.react.login.demo.security.jwt.AuthTokenFilter;
import com.spring.react.login.demo.security.jwt.CustomerPasswordEncoder;
import com.spring.react.login.demo.service.JwtUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntryPointJwt jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtuserDetailsService;

	@Autowired
	private AuthTokenFilter jwtRequestFilter;
	
	@Autowired
	private CustomerPasswordEncoder customerPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(jwtuserDetailsService).passwordEncoder(customerPasswordEncoder.getPasswordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity = httpSecurity.csrf().disable().cors().disable();
		
		httpSecurity = httpSecurity
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();
		
		httpSecurity = httpSecurity.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and();
		
		httpSecurity.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated();
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
