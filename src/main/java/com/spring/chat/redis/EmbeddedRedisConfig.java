/*

package com.spring.chat.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;




// This class is used to run an embedded Redis server when the profile is set to "local".
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {
	
	@Value("${spring.redis.port}")
	private int redisPort;

	private RedisServer redisServer;
	
	@PostConstruct
	public void redisServer() {
		RedisServer.builder().port(redisPort).setting("maxmemory 128M") // 최대 메모리 설정
				.build().start();
	}

	@PreDestroy
	public void stopRedis() {
		RedisServer.builder().port(redisPort).setting("maxmemory 128M").build().stop();
	}

}

*/