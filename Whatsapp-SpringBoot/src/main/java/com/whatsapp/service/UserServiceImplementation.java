package com.whatsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.whatsapp.exception.UserException;
import com.whatsapp.model.User;
import com.whatsapp.repository.UserRepository;
import com.whatsapp.request.UpdateUserRequest;
import com.whatsapp.security.JwtToken;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private JwtToken token;
	
	@Override
	public User findUserById(Integer id) throws UserException{
		Optional<User> opt  = urepo.findById(id);
		
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new UserException("user not found with id"+id);
	}

	@Override
	public User findUserProfile(String jwt) {
		String email = token.ExtractEmail(jwt);
		
		if(email==null)
		{
			throw new BadCredentialsException("recieved invalid token --- ");
			
		}
		User user = urepo.findByEmail(email);
		if(user==null)
		{
			throw new BadCredentialsException("uset not found with email--- ");

		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		
		
		User user = findUserById(userId);
		
		if(req.getFullName()!=null)
		{
			user.setFullName(req.getFullName());
		}
		if(req.getProfilePicture()!=null)
		{
			user.setProfilePicture(req.getProfilePicture());
		}
		return urepo.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		
		List<User> users = urepo.searchUser(query);
		return users;
	}

}
