package com.cimarasah.token.service;

import java.util.UUID;

import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;

public interface TokenService {

    Token createToken();

    void saveTokenData(UUID uuid, Object tokenData, String type);

    TokenData getTokenData(UUID uuid, String type);
}
