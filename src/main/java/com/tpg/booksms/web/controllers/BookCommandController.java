package com.tpg.booksms.web.controllers;

import com.tpg.booksms.services.UuidGenerationService;
import com.tpg.booksms.web.model.requests.NewBookRequest;
import com.tpg.booksms.web.model.responses.CreatedBookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/books")
public class BookCommandController implements HttpHeadersBuilder {
    private static final String BOOKS_URI = "/books";

    private final UuidGenerationService uuidGenerationService;

    public BookCommandController(UuidGenerationService uuidGenerationService) {
        this.uuidGenerationService = uuidGenerationService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedBookResponse> createBook(@Valid @RequestBody NewBookRequest request) {
        String uuid = uuidGenerationService.generateUuid();

        return new ResponseEntity<>(CreatedBookResponse.builder()
                .message(String.format("Saved new book %s", uuid))
                .uuid(uuid)
                .isbn(request.getIsbn()).build(),
                    generateHttpHeaders(BOOKS_URI, UUID.fromString(uuid)), CREATED);
    }
}
