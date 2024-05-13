package com.whatsapp.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.dto.UserDto;
import com.whatsapp.dto.UserDtoMapper;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.User;
import com.whatsapp.request.UpdateUserRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.UserService;


@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService)
	{
		this.userService=userService;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User>  getUserProfileHandler(@RequestHeader("Authorization") String token)
	{
		User user = userService.findUserProfile(token);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q)
	{
		List<User> users = userService.searchUser(q);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<HashSet<User>> searchUserByName(@RequestParam("name") String name)
	{
		System.out.println("search User-----");
		
		
		List<User> users = userService.searchUser(name);
				
		HashSet<User> set = new HashSet<>(users);
		
		
		
		
		System.out.println("search result "+set);
		
		return new ResponseEntity<>(set,HttpStatus.ACCEPTED);
		
		
		
		
	}

	
	@PutMapping("update/{userId}")
	public ResponseEntity<User> updateUserHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token)
	throws UserException
	{
		User user=userService.findUserProfile(token);
		User u =userService.updateUser(user.getId(), req);
		
		ApiResponse res= new ApiResponse("user updated successfully",true);
		
		System.out.println(res);
		
		return new ResponseEntity<>(u,HttpStatus.ACCEPTED);
	}

}
