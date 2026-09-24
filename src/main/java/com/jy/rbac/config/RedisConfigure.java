package com.jy.rbac.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfigure {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // key/hashKey 用字符串序列化（redis-cli 里 key 可读）
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // value 用 Jackson JSON 序列化（redis-cli 里 value 是可读 JSON）
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        return template;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        // 支持 LocalDateTime（UserVO 里有 createTime/updateTime 等时间字段）
        om.registerModule(new JavaTimeModule());
        // 时间输出为 "yyyy-MM-dd HH:mm:ss" 风格字符串，而不是数字时间戳
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 写入 @class 类型信息，取回时还原为原始类型
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return om;
    }
}
