package com.tpg.booksms.web.model.requests;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewBookRequest {
    private String isbn;
    private String title;
    private String description;
    private String edition;
    private BigDecimal price;
    private OffsetDateTime dateLaunched;
    private int quantity;
}
