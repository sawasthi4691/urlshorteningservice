package com.intuit.urlshortening.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.urlshortening.system.model.URLDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private ObjectMapper objectMapper;

    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(ObjectMapper objectMapper, RedisConnectionFactory redisConnectionFactory) {
        this.objectMapper = objectMapper;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * Setting up the Redis template object.
     * @return : RedisTemplate
     */
    @Bean
    public RedisTemplate<String, URLDto> redisTemplate() {
        final Jackson2JsonRedisSerializer<URLDto> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(URLDto.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        final RedisTemplate<String, URLDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
