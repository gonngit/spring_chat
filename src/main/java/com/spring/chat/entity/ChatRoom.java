package com.spring.chat.entity;

import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.entity.Chat;
import com.spring.chat.entity.ChatType;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
	
	private static final long serialVersionUID = 6494678977089006639L;
	
	private String roomId;
	private String name;
	
	
	public static ChatRoom create(String name) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.name = name;
		chatRoom.roomId = UUID.randomUUID().toString();
		return chatRoom;
	}
}
