package com.tpg.booksms.web.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewBookRequest {
    @NotEmpty(message = "ISBN must not be empty")
    private String isbn;

    @NotEmpty(message = "title must not be empty")
    private String title;

    private String description;
    private String edition;

    @NotNull(message = "price must be defined")
    private BigDecimal price;

    private OffsetDateTime dateLaunched;

    @Min(value = 1L, message = "quantity must be greater than zero")
    private int quantity;
}
