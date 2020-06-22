package com.tpg.booksms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpg.booksms.services.UuidGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public abstract class WebMvcBasedTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UuidGenerationService uuidGenerationService;

    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }
}
