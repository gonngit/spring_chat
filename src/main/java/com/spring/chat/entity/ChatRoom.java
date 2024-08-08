package com.spring.chat.entity;

import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.entity.Message;
import com.spring.chat.entity.MessageType;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
	private String roomId;
	private String name;
	
	
	public static ChatRoom create(String name) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.name = name;
		chatRoom.roomId = UUID.randomUUID().toString();
		return chatRoom;
	}
}
