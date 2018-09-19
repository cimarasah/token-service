package com.cimarasah.token.infrastructure.persistence;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cimarasah.token.config.RedisConfiguration;
import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;
import com.cimarasah.token.domain.repository.TokenRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisTokenRepository implements TokenRepository {

    private RedisTemplate<String, Token> redisTemplate;
    private HashOperations hashOps;
    private RedisConfiguration redisConfiguration;

    @Inject
    public RedisTokenRepository(RedisTemplate redisTemplate, RedisConfiguration redisConfiguration) {
        this.redisTemplate = redisTemplate;
        this.redisConfiguration = redisConfiguration;
    }

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void saveToken(Token token) {
        String key = token.getUuidString();
        hashOps.put(key, key, token);
        redisTemplate.expire(key, redisConfiguration.getTimeout(), redisConfiguration.getTimeUnit());
    }

    @Override
    public Token findToken(UUID uuid) {
        return (Token) hashOps.get(uuid.toString(), uuid.toString());
    }

    @Override
    public void addTokenData(Token token, Object tokenData, String type) {
        hashOps.put(token.getUuidString(), getHashKey(token, type), tokenData);
    }

    @Override
    public TokenData findTokenData(Token token, String type) {
        return (TokenData) hashOps.get(token.getUuidString(), getHashKey(token, type));
    }

    private String getHashKey(Token token, String type) {
        return String.format("%s:%s", token.getUuidString(), type);
    }
}
