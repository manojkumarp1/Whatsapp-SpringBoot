package com.whatsapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.Message;
import com.whatsapp.model.User;
import com.whatsapp.repository.MessageRepository;
import com.whatsapp.request.SendmessageRequest;


@Service
public class MessageServiceImplementation implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	@Override
	public Message sendMessage(SendmessageRequest req) throws UserException, ChatException {
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException,UserException {
		Chat chat=chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser))
		{
			throw new UserException("you not related to this chat"+chat.getId());
		}
		List<Message> messages = messageRepository.findByChatId(chat.getId());
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		
		Optional<Message> opt = messageRepository.findById(messageId);
		
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new MessageException("message not found with id "+messageId);
		
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException {
		Optional<Message> message = messageRepository.findById(messageId);
		
		if(message.get().getUser().getId().equals(reqUser.getId()))
		{
			messageRepository.deleteById(messageId);
		}
		throw new UserException("you cant delete another user message"+reqUser.getFullName());

	}

}
