package com.cimarasah.token.web.model;

import com.cimarasah.token.domain.model.TokenData;
import com.cimarasah.token.domain.model.data.StoreStamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TokenDataRequest {

    @NotNull
    private DataType type;

    @Valid
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = StoreStamp.class, name = "stamp")
    })
    private TokenData data;

    public String getType() {
        return type.code;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = DataType.fromString(type);
    }

    @JsonIgnore
    public void setType(DataType type) {
        this.type = type;
    }

    public TokenData getData() {
        return data;
    }

    public void setData(TokenData data) {
        this.data = data;
    }
}
