package com.cimarasah.token.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;
import com.cimarasah.token.domain.model.TokenNotFoundException;
import com.cimarasah.token.domain.repository.TokenRepository;
import com.cimarasah.token.service.TokenService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultTokenService implements TokenService {

    private final TokenRepository tokenRepository;

    @Inject
    public DefaultTokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token createToken() {
        Token token = new Token(UUID.randomUUID());
        tokenRepository.saveToken(token);
        return token;
    }

    @Override
    public void saveTokenData(UUID uuid, Object tokenData, String type) {
        Token token = Optional.ofNullable(tokenRepository.findToken(uuid))
                .orElseThrow(() -> new TokenNotFoundException(String.format("Token %s não encontrado.", uuid.toString())));
        if(StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("Tipo não pode ser vazio.");
        }

        tokenRepository.addTokenData(token, tokenData, type);
    }

    @Override
    public TokenData getTokenData(UUID uuid, String type) {
        Token token = Optional.ofNullable(tokenRepository.findToken(uuid))
                .orElseThrow(() -> new TokenNotFoundException(String.format("Token %s não encontrado.", uuid.toString())));
        if(StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("Tipo não pode ser vazio.");
        }

        return tokenRepository.findTokenData(token, type);
    }
}
