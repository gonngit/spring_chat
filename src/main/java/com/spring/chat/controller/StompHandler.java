package com.spring.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.security.JwtTokenProvider;
import com.spring.chat.service.ChatService;
import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;
import java.util.Optional;
import java.security.Principal;




// when a user connects to the WebSocket, it validates the token
@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
	// ChannelInterceptor is an interface that provides methods to intercept messages and perform operations before sending or after receiving messages
	
	
	private final JwtTokenProvider jwtTokenProvider;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatService chatService;
	

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
	    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

	    if (StompCommand.CONNECT == accessor.getCommand()) {
	        jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
		} else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
			String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoom"));
			String sessionId = (String) message.getHeaders().get("simpSessionId");
			chatRoomRepository.setUserJoin(roomId, sessionId);
			chatRoomRepository.addUserCount(roomId);
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
			chatService.sendChat(Chat.builder().type(ChatType.JOIN).roomId(roomId).sender(name).build());
		}  else if (StompCommand.DISCONNECT == accessor.getCommand()) {
			String sessionId = (String) message.getHeaders().get("simpSessionId");
			String roomId = chatRoomRepository.getUserJoinRoomId(sessionId);
			chatRoomRepository.subUserCount(roomId);
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
			chatService.sendChat(Chat.builder().type(ChatType.LEAVE).roomId(roomId).sender(name).build());
			chatRoomRepository.removeUserJoin(sessionId);
		}	        
	    return message;
	}
	
}

