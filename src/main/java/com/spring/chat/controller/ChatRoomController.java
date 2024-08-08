package com.spring.chat.controller;

import com.spring.chat.entity.ChatRoom;
import com.spring.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

 private final com.spring.chat.repository.ChatRoomRepository chatRoomRepository;

 // chat list
 @GetMapping("/room")
 public String rooms(Model model) {
     return "/chat/room";
 }
 
 // get all chats
 @GetMapping("/rooms")
 @ResponseBody
 public List<ChatRoom> room() {
     return chatRoomRepository.getChatRooms();
 }
 
 // create chat
 @PostMapping("/room")
 @ResponseBody
 public ChatRoom createRoom(@RequestParam("name") String name) {  //("name") parameter name retrieval error
     return chatRoomRepository.createChatRoom(name);
 }
 
 // enter chat
 @GetMapping("/room/join/{roomId}")
 public String roomDetail(Model model, @PathVariable("roomId") String roomId) {
     model.addAttribute("roomId", roomId);
     return "/chat/roomdetail";
 }
 
 // find chat by id
 @GetMapping("/room/{roomId}")
 @ResponseBody
 public ChatRoom roomInfo(@PathVariable("roomId") String roomId) {
     return chatRoomRepository.findRoomById(roomId);
 }
 
}
