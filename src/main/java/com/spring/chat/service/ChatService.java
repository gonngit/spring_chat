package com.spring.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;


@RequiredArgsConstructor
@Service
public class ChatService {
	
	private final ChannelTopic channelTopic;
	private final RedisTemplate redisTemplate;
	private final ChatRoomRepository chatRoomRepository;
	
	
	
	public String getRoomId(String dest) {
		int lastIndex = dest.lastIndexOf("/");
		if (lastIndex != -1) {
			return dest.substring(lastIndex + 1);
		} else {
			return "InvalidRoom";
		}
	}
	
	public void sendChat(Chat chat) {
		chat.setUserCount(chatRoomRepository.getUserCount(chat.getRoomId()));
		if (ChatType.JOIN.equals(chat.getType())) {
			chat.setContent(chat.getSender() + " has joined the chat room");
			chat.setSender("[System]");
		} else if (ChatType.LEAVE.equals(chat.getType())) {
			chat.setContent(chat.getSender() + " has left the chat room");
			chat.setSender("[System]");
		}
		redisTemplate.convertAndSend(channelTopic.getTopic(), chat);
	}
	

}
