package com.spring.chat.service;

import com.spring.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import jakarta.annotation.PostConstruct;
import java.io.IOException;




// 
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
	
	private final ObjectMapper objectMapper;
	private Map<String, ChatRoom> chatRooms;
	
	@PostConstruct
	private void init() {
		chatRooms = new LinkedHashMap<>();
	}
	
	public ChatRoom createRoom(String name) {
		String rnd = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder().roomId(rnd).name(name).build();
		chatRooms.put(rnd, chatRoom);
		return chatRoom;
	}
	
	public List<ChatRoom> getChatRooms() {
		return new ArrayList<>(chatRooms.values());
	}
	
	public ChatRoom findRoomById(String roomId) {
		return chatRooms.get(roomId);
	}
	
	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	

}
