package com.spring.chat.entity;

import com.spring.chat.entity.ChatType;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
public class Chat {
	
	public Chat() {
	}
	
	@Builder
	public Chat(ChatType type, String roomId, String sender, String content, long userCount) {
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.content = content;
		this.userCount = userCount;
	}
	
	private ChatType type;
	private String roomId;
	private String sender;
	private String content;
	private long userCount;

}
