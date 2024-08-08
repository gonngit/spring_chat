package com.spring.chat.controller;

import com.spring.chat.entity.ChatRoom;
import com.spring.chat.entity.Message;
import com.spring.chat.entity.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {
	
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/chat/message")
	public void message(Message message) {
		if (MessageType.JOIN.equals(message.getType())) {
			message.setContent(message.getSender() + " has joined the chat");
		}
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

}
