package com.fsd.sdp.asthetica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.sdp.asthetica.model.User;
import com.fsd.sdp.asthetica.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public String adduser(User user) {
		repository.save(user);
		return "User Registered Successfully";
	}

	@Override
	public User checkuserlogin(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Override
	public List<User> displayusers() {
		
		return repository.findAll();
	}

	@Override
	public String deleteuser(int cid) {
		Optional<User> user=repository.findById(cid);
		if(user.isPresent()) {
			repository.deleteById(cid);
			return "User Deleted Successfully";
		}else {
			return "User Id Not Found";
		}
		
	}

}
