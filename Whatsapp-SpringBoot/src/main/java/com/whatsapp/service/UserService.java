package com.whatsapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whatsapp.exception.UserException;
import com.whatsapp.model.User;
import com.whatsapp.request.UpdateUserRequest;
@Service
public interface UserService {
	
	public User findUserById(Integer id) throws UserException;
	public User findUserProfile(String jwt);
	public User updateUser(Integer userId,UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);
}
