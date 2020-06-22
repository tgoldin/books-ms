package com.tpg.booksms.web.controllers;

import com.tpg.booksms.services.UuidGenerationService;
import com.tpg.booksms.web.model.requests.NewBookRequest;
import com.tpg.booksms.web.model.responses.CreatedBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.tpg.booksms.BookUtils.NEW_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
public class BookCommandControllerTests {
    private static final String UUID = NEW_UUID.toString();

    private NewBookRequest newBookRequest;

    @Mock
    private UuidGenerationService uuidGenerationService;

    @InjectMocks
    private BookCommandController controller;

    @BeforeEach
    public void setUp() {
        newBookRequest = NewBookRequest.builder().title("War and Peace").isbn("ABC-1234").build();
    }

    @Test
    public void givenANewBook_whenPostingNewBook_thenBookIsCreatedAndResponseStatusIsCreated() {
        when(uuidGenerationService.generateUuid()).thenReturn(UUID);

        ResponseEntity<CreatedBookResponse> actual = controller.createBook(newBookRequest);

        assertThat(actual.getBody().getUuid()).isNotNull();
        assertThat(actual.getBody().getIsbn()).isEqualTo(newBookRequest.getIsbn());
        assertThat(actual.getHeaders().get("Location")).hasSize(1);
        assertThat(actual.getHeaders().get("Location")).contains(String.format("/books/%s", UUID));
        assertThat(actual.getStatusCode()).isEqualTo(CREATED);
    }
}
