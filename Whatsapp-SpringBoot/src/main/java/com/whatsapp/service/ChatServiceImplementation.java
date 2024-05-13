package com.whatsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;
import com.whatsapp.repository.ChatRepository;


@Service
public class ChatServiceImplementation implements ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Chat createChat(User reqUser, Integer userId) throws UserException {
		User user=userService.findUserById(userId);
		Chat isChatExist=chatRepository.findSingleChatByUserIds(user, reqUser);
		
		if(isChatExist!=null)
		{
			return isChatExist;
		}
		
		Chat chat = new Chat();
		
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		
		chat.setGroup(false);
		
		return chatRepository.save(chat);
		
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);
		
		if(chat.isPresent())
		{
			return chat.get();
		}
		throw new ChatException("Chat not found with id"+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserById(userId);
		List<Chat> chats = chatRepository.findChatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		
		Chat group = new Chat();
		
		group.setGroup(true);
		group.setChatImage(req.getChatImage());
		group.setChatName(req.getChatName());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);
		group.getUsers().add(reqUser);
		
		
		for(Integer userId:req.getUserIds())
		{
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}
		
		return chatRepository.save(group);
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws UserException, ChatException {
		
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		
		
		
		if(opt.isPresent())
		{
			Chat chat =opt.get();
			if(chat.getAdmins().contains(reqUser))
			{
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			}
			else
			{
				throw new UserException("You are not admin");
			}
		}
		throw new ChatException("chat not found with id"+chatId);
		
		
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isPresent())
		{
			Chat chat = opt.get();
			if(chat.getUsers().contains(reqUser))
			{
				chat.setChatName(groupName);
				chatRepository.save(chat);
			}
			throw new UserException("your are not member of group");
		}
		
		
		
		throw new ChatException("chat not found with id"+chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
		
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		
		
		
		if(opt.isPresent())
		{
			Chat chat =opt.get();
			if(chat.getAdmins().contains(reqUser))
			{
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			}
			else if(chat.getUsers().contains(reqUser))
			{
				if(user.getId().equals(reqUser.getId()))
				{
					chat.getUsers().remove(user);
					return chatRepository.save(chat);
				}
			}
			
				throw new UserException("You cant remove antoher user");
			
		}
		throw new ChatException("chat not found with id"+chatId);
		
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		
		if(opt.isPresent())
		{
			Chat chat = opt.get();
			chatRepository.deleteById(chat.getId());
			
		}
		throw new ChatException("chat not found with id"+chatId);
		
		
		
	}

}
