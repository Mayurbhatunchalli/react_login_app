package com.spring.react.login.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.react.login.demo.entity.User;
import com.spring.react.login.demo.entity.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	User save(UserDTO userDTO);

}
