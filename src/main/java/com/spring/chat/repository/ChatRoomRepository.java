package com.spring.chat.repository;

import com.spring.chat.entity.ChatRoom;
import com.spring.chat.redis.RedisSubscriber;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ChatRoomRepository {
	
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	public static final String USER_COUNT = "USER_COUNT";
	public static final String USER_JOIN = "USER_JOIN"; // sessionId & roomId
	private static final Logger logger = LoggerFactory.getLogger(ChatRoomRepository.class);
	
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> opsHashUserJoin;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> opsValue;
	
	
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
	
	public List<ChatRoom> getChatRooms() {
		return opsHashChatRoom.values(CHAT_ROOMS);
	}

	public ChatRoom findRoomById(String id) {
	    return opsHashChatRoom.get(CHAT_ROOMS, id);
	}
	
	public void setUserJoin(String roomId, String sessionId) {
		opsHashUserJoin.put(USER_JOIN, sessionId, roomId);
	}
	
	public void removeUserJoin(String sessionId) {
		opsHashUserJoin.delete(USER_JOIN, sessionId);
	}
	
	public String getUserJoinRoomId(String sessionId) {
		return opsHashUserJoin.get(USER_JOIN, sessionId);
	}
	
	public long getUserCount(String roomId) {
		return Long.valueOf(Optional.ofNullable(opsValue.get(USER_COUNT + "_" + roomId)).orElse("0"));
	}
	
	public long addUserCount(String roomId) {
		return Optional.ofNullable(opsValue.increment(USER_COUNT + "_" + roomId)).orElse(0L);
	}
	
	public long subUserCount(String roomId) {
		return Optional.ofNullable(opsValue.decrement(USER_COUNT + "_" + roomId)).filter(count -> count > 0).orElse(0L);
	}
 
}