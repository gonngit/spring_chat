package com.spring.chat.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;



// when a user connects to the WebSocket, it validates the token
@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
	// ChannelInterceptor is an interface that provides methods to intercept messages and perform operations before sending or after receiving messages
	
	
	private final JwtTokenProvider jwtTokenProvider;
	

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
	    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

	    if (StompCommand.CONNECT == accessor.getCommand()) {
	        jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
	    }
	    return message;
	}
}

