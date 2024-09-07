package com.spring.chat.controller;

import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final ChannelTopic channelTopic;
	private final JwtTokenProvider jwtTokenProvider;
	
	@MessageMapping("/chat/message")
	public void message(Chat message, @Header("token") String token) {
		String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		message.setSender(nickname);
		
		if (ChatType.JOIN.equals(message.getType())) {
			message.setContent(nickname + " has joined the chat");
		}
		//messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
		
	}

}
