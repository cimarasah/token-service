package com.cimarasah.token.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;
import com.cimarasah.token.service.TokenService;
import com.cimarasah.token.web.model.DataType;
import com.cimarasah.token.web.model.TokenDataRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class TokenController implements TokenResource {

    private final TokenService tokenService;

    @Inject
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public ResponseEntity<Token> token() {
        return ResponseEntity.ok(tokenService.createToken());
    }

    @Override
    public void addTokenData(@PathVariable UUID uuid, @RequestBody @Valid TokenDataRequest tokenDataRequest) {
        tokenService.saveTokenData(uuid, tokenDataRequest.getData(), tokenDataRequest.getType());
    }

    @Override
    public ResponseEntity<TokenData> getTokenData(@PathVariable UUID uuid, @PathVariable String type) {
        return ResponseEntity.ok(tokenService.getTokenData(uuid, DataType.fromString(type).code));
    }
}
