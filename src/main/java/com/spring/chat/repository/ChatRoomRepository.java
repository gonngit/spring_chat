package com.spring.chat.repository;

import com.spring.chat.entity.ChatRoom;
import com.spring.chat.redis.RedisSubscriber;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatRoomRepository.class);
	private final RedisMessageListenerContainer redisMessageListener;
	private final RedisSubscriber redisSubscriber;
	
	
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	private Map<String, ChannelTopic> topics;
	
	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
		topics = new ConcurrentHashMap<>();
	}
	
	// save chat room in redis hash
	public ChatRoom createChatRoom(String name) {
		logger.info("Creating chat room: {}", name);
	    try {
	        ChatRoom chatRoom = ChatRoom.create(name);
	        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
	        return chatRoom;
	    } catch (Exception e) {
	        // Log the error
	        throw new RuntimeException("Failed to create chat room", e);
	    }
	}
	
	public void joinChatRoom(String roomId) {
		logger.info("Joining chat room: {}", roomId);
	    getTopic(roomId);  // This will create the topic if it doesn't exist
	}
	
	public List<ChatRoom> getChatRooms() {
		return opsHashChatRoom.values(CHAT_ROOMS);
	}

	public ChatRoom findRoomById(String id) {
	    ChatRoom room = opsHashChatRoom.get(CHAT_ROOMS, id);
	    if (room == null) {
	        logger.warn("Chat room not found: {}", id);
	    }
	    return room;
	}
	
	public ChannelTopic getTopic(String roomId) {
	    return topics.computeIfAbsent(roomId, k -> {
	        ChannelTopic topic = new ChannelTopic(roomId);
	        redisMessageListener.addMessageListener(redisSubscriber, topic);
	        return topic;
	    });
	}

 
 
}