package com.spring.chat.controller;

import com.spring.chat.entity.ChatRoom;
import com.spring.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
	
	private final ChatService chatService;
	
	@PostMapping
	public ChatRoom createRoom(@RequestParam("name") String name) {
		System.out.println("hello");
		return chatService.createRoom(name);
	}
	
	@GetMapping
	public List<ChatRoom> getChatRooms() {
		return chatService.getChatRooms();
	}

}
