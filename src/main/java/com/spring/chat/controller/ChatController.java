package com.spring.chat.controller;

import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {
	
	private final RedisPublisher redisPublisher;
	private final ChatRoomRepository chatRoomRepository;
	
	@MessageMapping("/chat/message")
	public void message(Chat message) {
		if (ChatType.JOIN.equals(message.getType())) {
			message.setContent(message.getSender() + " has joined the chat");
		}
		//messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
		redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
		
	}

}
