package com.spring.chat.controller;

import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.security.JwtTokenProvider;
import com.spring.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.Header;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatService chatService;
	
	@MessageMapping("/chat/message")
	public void message(Chat message, @Header("token") String token) {
		String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		message.setSender(nickname);
		message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
		chatService.sendChat(message);
		
	}

}
