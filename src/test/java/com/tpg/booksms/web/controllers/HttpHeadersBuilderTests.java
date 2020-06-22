package com.tpg.booksms.web.controllers;

import com.tpg.booksms.BookUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static com.tpg.booksms.BookUtils.NEW_UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersBuilderTests implements HttpHeadersBuilder {
    @Test
    public void givenAUuid_whenBuilt_headersContainLocationValue() {
        HttpHeaders actual = generateHttpHeaders("/books", NEW_UUID);
        List<String> actualList = actual.get("Location");
        assertThat(actualList.size()).isEqualTo(1);
        assertThat(actualList.get(0)).isEqualTo(String.format("/books/%s", NEW_UUID));
    }

    @Test
    public void givenNullUuid_whenBuilt_headersIsEmpty() {
        HttpHeaders actual = generateHttpHeaders("/books", null);
        List<String> actualList = actual.get("Location");
        assertThat(actual).isEmpty();
    }
}
