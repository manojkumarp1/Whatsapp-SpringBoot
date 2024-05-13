package com.whatsapp.dto;

import com.whatsapp.model.User;

public class UserDtoMapper {
	
	
	 public static UserDto toUserDto(User user) 
	 {
		 UserDto userDto = new UserDto();
	     userDto.setId(user.getId());
	     userDto.setFullName(user.getFullName());
	     userDto.setEmail(user.getEmail());
	     userDto.setProfilePicture(user.getProfilePicture());
	        
	        return userDto;
	    }

}
