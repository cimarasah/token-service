package com.cimarasah.token.domain.repository;

import java.util.UUID;

import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;

public interface TokenRepository {

    void saveToken(Token token);

    Token findToken(UUID uuid);

    void addTokenData(Token token, Object tokenData, String type);

    TokenData findTokenData(Token token, String type);
}
