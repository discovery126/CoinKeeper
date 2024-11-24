package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.denis.coinkeeper.api.entities.FinanceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDto {

    @Null(message = "financeId must be null when creating a new record.")
    private Long financeId;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Category cannot be blank.")
    private String category;

    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be positive.")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 fractional digits.")
    private BigDecimal price;

    // INCOME - доходы, // EXPENSES - расходы
    @NotNull(message = "Finance type is required.")
    @JsonProperty("finance_type")
    //TODO
    // create handler for empty string finance type cause it throw exception without handler
    // OR change FinanceType to String
    private FinanceType financeType;

    @Null(message = "User ID must be null for security reasons.")
    private Long userId;

    @Builder.Default
    private LocalDateTime addedAt = LocalDateTime.now();
}
