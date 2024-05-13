package com.whatsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.model.Message;

@RestController
@CrossOrigin(origins = "*")
public class RealTimeChat {
	
	@Autowired
	private SimpMessagingTemplate simpMassagingTemplate;
	
	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message recieveMessage(@Payload Message message)
	{
		System.out.println("this is  me");
		System.out.println(message.getContent());
		
		simpMassagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(),message);
		
		return message;
	}

}
