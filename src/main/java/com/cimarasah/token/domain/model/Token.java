package com.cimarasah.token.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.nonNull;

public class Token {

    private UUID uuid;

    private LocalDateTime creationDate;

    public Token() { }

    public Token(UUID uuid) {
        this.uuid = uuid;
        this.creationDate = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public String getUuidString() {
        return nonNull(uuid) ? uuid.toString() : "";
    }
}
