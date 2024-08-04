package com.spring.chat.entity;

import com.spring.chat.service.ChatService;
import com.spring.chat.entity.Message;
import com.spring.chat.entity.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.Set;


@Getter
public class ChatRoom {
	private String roomId;
	private String name;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	@Builder
	public ChatRoom(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
	}
	
	public void handleActions(WebSocketSession session, Message message, ChatService chatService) {

		if (message.getType().equals(MessageType.JOIN)) {
			sessions.add(session);
			message.setContent(message.getSender() + " has joined the chat.");
		}
		send(message, chatService);
	}
	
	public <T> void send(T message, ChatService chatService) {
		sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
	}
}
