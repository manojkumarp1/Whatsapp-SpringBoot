package com.whatsapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.model.User;
import com.whatsapp.repository.UserRepository;
import com.whatsapp.security.JwtToken;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	JwtToken jwtToken;
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/signin")
	public ResponseEntity<Object> Login(@RequestBody User u)
	{
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(u.getEmail(),u.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtToken.generateToken(authentication);
		
		return ResponseEntity.ok().body(Map.of("token",token,"tokentype","Bearer"));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Object> createUser(@RequestBody User u)
	{
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return ResponseEntity.ok().body(urepo.save(u));
	}
	

}
