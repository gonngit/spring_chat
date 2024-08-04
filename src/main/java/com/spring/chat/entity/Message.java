package com.spring.chat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	// join = enter, chat = talk
	public enum MessageType {
		CHAT, JOIN, LEAVE
	}
	
	private MessageType type;
	private String roomId;
	private String sender;
	private String content;

}
