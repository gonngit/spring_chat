package com.spring.chat.entity;

import com.spring.chat.entity.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	private MessageType type;
	private String roomId;
	private String sender;
	private String content;

}
