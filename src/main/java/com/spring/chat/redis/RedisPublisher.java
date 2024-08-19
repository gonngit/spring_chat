package com.spring.chat.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.spring.chat.entity.Chat;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
	
	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(ChannelTopic topic, Chat message) {
	    redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}