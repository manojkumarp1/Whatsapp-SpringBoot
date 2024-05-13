package com.whatsapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	
	@Autowired
	JwtToken jwtToken;
	
	
	UserDetailsService userDetailService;
	
	public JwtAuthenticationFilter(@Lazy UserDetailsService userDetailsService)
	{
		this.userDetailService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
		throws ServletException,IOException
	{
		String token=getToken(request);
		
		if(token!=null && jwtToken.isValidToken(token))
		{
			UserDetails userDetails = userDetailService.loadUserByUsername(jwtToken.ExtractEmail(token));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
	
	public String getToken(HttpServletRequest request)
	{
		String token = request.getHeader("Autorisation");
		
		if(token!=null && token.startsWith("Beater"))
		{
			return token.substring(7);
			
		}
		return null;
	}
	

}
