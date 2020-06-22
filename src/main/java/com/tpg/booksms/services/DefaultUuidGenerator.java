package com.tpg.booksms.services;

import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class DefaultUuidGenerator implements UuidGenerationService {
    @Override
    public String generateUuid() {
        return randomUUID().toString();
    }
}
