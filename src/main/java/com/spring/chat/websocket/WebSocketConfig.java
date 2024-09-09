package com.spring.chat.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.spring.chat.controller.StompHandler;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.messaging.simp.config.ChannelRegistration;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private final StompHandler stompHandler;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/sub");
		config.setApplicationDestinationPrefixes("/pub");
	}
	
	// to be fixed (because of security)
	// when ("*"), setAllowedOrigins can not be used
    @Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
	}
    
    // make StompHandler to check the token when a user connects to the WebSocket
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompHandler);
	}

    
}
