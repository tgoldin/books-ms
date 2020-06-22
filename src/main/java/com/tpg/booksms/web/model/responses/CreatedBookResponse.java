package com.tpg.booksms.web.model.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatedBookResponse {
    private final String message;
    private final String uuid;
    private final String isbn;
}
