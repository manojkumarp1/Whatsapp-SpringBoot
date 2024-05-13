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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Message;
import com.whatsapp.model.User;
import com.whatsapp.request.SendmessageRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.MessageService;
import com.whatsapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendmessageRequest req,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		System.out.println("mango maoj");
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		Message message = messageService.sendMessage(req);
		System.out.println("completed");
		return new ResponseEntity<Message>(message,HttpStatus.OK);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatsMessageHandler(@PathVariable("chatId") Integer chatId,@RequestHeader("Authorization") String jwt)
	throws UserException,ChatException
	{
		
		User user = userService.findUserProfile(jwt);
		
		List<Message> messages = messageService.getChatMessages(chatId, user);
		return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable("messageId") Integer messageId,@RequestHeader("Authorization") String jwt)
	throws UserException,MessageException
	{
		User user = userService.findUserProfile(jwt);
		
		messageService.deleteMessage(messageId, user);
		
		ApiResponse res=new ApiResponse("message deleted successfully",false);
		
		
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
		
	}
}
