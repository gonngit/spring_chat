package com.spring.chat.redis;

import com.spring.chat.entity.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber {
	
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

	public void sendMessage(String pubMessage) {
        try {
        	Chat message = objectMapper.readValue(pubMessage, Chat.class); 
        	messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}