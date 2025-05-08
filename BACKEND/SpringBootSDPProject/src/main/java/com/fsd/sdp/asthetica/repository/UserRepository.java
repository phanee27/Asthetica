package com.fsd.sdp.asthetica.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.sdp.asthetica.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsernameAndPassword(String username, String password);
	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> findByStatusIsNotNull();
}
