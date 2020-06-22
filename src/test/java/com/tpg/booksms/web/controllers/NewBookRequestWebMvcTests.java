package com.tpg.booksms.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpg.booksms.services.UuidGenerationService;
import com.tpg.booksms.web.model.requests.NewBookRequest;
import com.tpg.booksms.web.model.responses.CreatedBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.tpg.booksms.BookUtils.NEW_UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookCommandController.class)
public class NewBookRequestWebMvcTests {

    private static final String UUID = NEW_UUID.toString();

    private ObjectMapper objectMapper;

    @MockBean
    private UuidGenerationService uuidGenerationService;

    @Autowired
    private MockMvc mockMvc;

    private NewBookRequest newBookRequest;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        newBookRequest = NewBookRequest.builder().title("Surrogates")
                .description("In the not so distant future.").isbn("ABD-28278").build();
    }

    @Test
    public void givenANewBook_whenPostingBook_thenBookIsCreatedAndResponseStatusIsCreated() throws Exception {
        String json = objectMapper.writeValueAsString(newBookRequest);

        when(uuidGenerationService.generateUuid()).thenReturn(UUID);

        CreatedBookResponse response = CreatedBookResponse.builder()
                .message(String.format("Saved new book %s", UUID))
                .uuid(UUID)
                .isbn(newBookRequest.getIsbn()).build();

        String expectedJsonResponse = objectMapper.writeValueAsString(response);

        mockMvc.perform(post("/books")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", is(equalTo(String.format("/books/%s", UUID)))))
            .andExpect(content().json(expectedJsonResponse));
    }
}
