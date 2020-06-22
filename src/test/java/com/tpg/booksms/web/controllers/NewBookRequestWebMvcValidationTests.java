package com.tpg.booksms.web.controllers;

import com.tpg.booksms.web.WebMvcBasedTest;
import com.tpg.booksms.web.model.requests.NewBookRequest;
import com.tpg.booksms.web.model.responses.CreatedBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;

import static com.tpg.booksms.BookUtils.NEW_UUID;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookCommandController.class)
public class NewBookRequestWebMvcValidationTests extends WebMvcBasedTest {

    private static final String UUID = NEW_UUID.toString();

    private NewBookRequest newBookRequest;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        newBookRequest = NewBookRequest.builder()
                .title("Surrogates")
                .description("In the not so distant future.")
                .isbn("ABD-28278")
                .price(new BigDecimal(1.99))
                .quantity(1)
                .build();
    }

    @Test
    public void givenMissingTitle_whenPostingNewBook_thenBookIsNotCreatedAndBadRequestResponseIsReturned() throws Exception {
        newBookRequest.setTitle(null);

        postNewBook("title must not be empty");
    }

    private void postNewBook(String expectedMessage) throws Exception {
        String json = objectMapper.writeValueAsString(newBookRequest);

        mockMvc.perform(post("/books")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(header().doesNotExist("Location"))
            .andExpect(jsonPath("$.status", is(SC_BAD_REQUEST)))
            .andExpect(jsonPath("$.errors", hasSize(1)))
            .andExpect(jsonPath("$.errors", hasItem(expectedMessage)));

        verify(uuidGenerationService, never()).generateUuid();
    }

    @Test
    public void givenMissingIsbn_whenPostingNewBook_thenBookIsNotCreatedAndBadRequestResponseIsReturned() throws Exception {
        newBookRequest.setIsbn(null);

        postNewBook("ISBN must not be empty");
    }

    @Test
    public void givenMissingPrice_whenPostingNewBook_thenBookIsNotCreatedAndBadRequestResponseIsReturned() throws Exception {
        newBookRequest.setPrice(null);

        postNewBook("price must be defined");
    }

    @Test
    public void givenZeroQuantity_whenPostingNewBook_thenBookIsNotCreatedAndBadRequestResponseIsReturned() throws Exception {
        newBookRequest.setQuantity(0);
        postNewBook("quantity must be greater than zero");
    }

    @Test
    public void givenNegativeQuantity_whenPostingNewBook_thenBookIsNotCreatedAndBadRequestResponseIsReturned() throws Exception {
        newBookRequest.setQuantity(-1);
        postNewBook("quantity must be greater than zero");
    }
}
