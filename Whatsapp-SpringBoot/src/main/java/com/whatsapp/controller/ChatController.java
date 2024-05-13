package com.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;
import com.whatsapp.request.SingleChatRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.ChatService;
import com.whatsapp.service.GroupChatRequest;
import com.whatsapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization") String jwt)
	throws UserException
	{
		User reqUser=userService.findUserProfile(jwt);
		
		System.out.println(singleChatRequest.getUserId());
		
		Chat chat=chatService.createChat(reqUser,singleChatRequest.getUserId());
		System.out.println(chat);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest req,@RequestHeader("Authorization") String jwt)
	throws UserException
	{
		System.out.println("group");
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createGroup(req,reqUser);
		
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable("chatId") Integer chatId,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		
		
		Chat chat=chatService.findChatById(chatId);
		
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
	throws UserException
	{
		
		User reqUser=userService.findUserProfile(jwt);
		
		List<Chat> chat=chatService.findAllChatByUserId(reqUser.getId());
		
		
		return new ResponseEntity<List<Chat>>(chat,HttpStatus.OK);
		
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId") Integer chatId,@PathVariable("userId") Integer userId,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.addUserToGroup(userId,chatId,reqUser);
		
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable("chatId") Integer chatId,@PathVariable("userId") Integer userId,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.removeFromGroup(userId, chatId, reqUser);
		
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteGroupHandler(@PathVariable("chatId") Integer chatId,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		User reqUser=userService.findUserProfile(jwt);
		
		chatService.deleteChat(chatId,reqUser.getId());
		
		ApiResponse res = new ApiResponse("chat is deleted successfully",false);
		
		
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
		
	}

}
