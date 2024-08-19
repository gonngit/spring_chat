package com.spring.chat.entity;

import com.spring.chat.entity.ChatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
	
	private ChatType type;
	private String roomId;
	private String sender;
	private String content;

}
