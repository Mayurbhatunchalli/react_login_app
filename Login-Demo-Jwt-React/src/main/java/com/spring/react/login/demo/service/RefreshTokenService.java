package com.spring.react.login.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.react.login.demo.entity.RefreshToken;
import com.spring.react.login.demo.exception.TokenRefreshException;
import com.spring.react.login.demo.repository.RefreshTokenRepository;
import com.spring.react.login.demo.repository.UserRepository;

@Service
public class RefreshTokenService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepo;
	
	@Value("${jwt.refreshExpirationDateInMs}")
	private Long refreshExpirationDateInMs;
	
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepo.findByToken(token);
	}
	
	
	public RefreshToken createRefreshData(Long id) {
		
		RefreshToken refreshToken = new RefreshToken();
		
		refreshToken.setUser(userRepo.findById(id).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationDateInMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		refreshToken = refreshTokenRepo.save(refreshToken);
		
		return refreshToken;
		
	}
	
	public RefreshToken verificationToken(RefreshToken token) {
		
		if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
			
			refreshTokenRepo.delete(token);
			throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
		}
		
		return token;
		
	}
	
	@Transactional
	public int deleteUserById(Long id) {
		
		return refreshTokenRepo.deleteByUser(userRepo.findById(id).get());
		
		
	}

}
