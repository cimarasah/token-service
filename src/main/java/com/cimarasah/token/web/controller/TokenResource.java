package com.cimarasah.token.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cimarasah.token.domain.model.Token;
import com.cimarasah.token.domain.model.TokenData;
import com.cimarasah.token.web.handler.ErrorMessage;
import com.cimarasah.token.web.model.TokenDataRequest;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "Token", description = "Api responsável pela criação do token da proposta.")
public interface TokenResource {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = Token.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    @ApiOperation(value = "Executa o serviço responsável pela criação do token.")
    @PostMapping(path = "/")
    ResponseEntity<Token> token();

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    @ApiOperation(value = "Executa o serviço responsável por adicionar dados ao token informado.")
    @PostMapping(path = "/{uuid}")
    void addTokenData(@PathVariable UUID uuid, @RequestBody @Valid TokenDataRequest tokenDataRequest);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = TokenData.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    @ApiOperation(value = "Executa o serviço responsável por recuperar os dados do token informado.")
    @GetMapping(path = "/{uuid}/{type}")
    ResponseEntity<TokenData> getTokenData(@PathVariable UUID uuid, @PathVariable String type);
}
