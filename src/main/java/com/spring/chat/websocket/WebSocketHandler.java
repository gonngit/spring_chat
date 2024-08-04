package com.spring.chat.websocket;

import com.spring.chat.service.ChatService;
import com.spring.chat.entity.Message;
import com.spring.chat.entity.ChatRoom;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler{
	
	private final ObjectMapper objectMapper;
	private final ChatService chatService;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("received message", payload); // payload is a string data in JSON format
		Message msg = objectMapper.readValue(payload, Message.class); // .class is the class for the JSON date to be converted to
		ChatRoom chatRoom = chatService.findRoomById(msg.getRoomId());
		chatRoom.handleActions(session, msg, chatService);
		
	}

}
	