package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.denis.coinkeeper.api.entities.FinanceType;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDto {

    @Null
    private Long financeId;

    private String name;

    private String category;

    private Long price;

    // INCOME - доходы, // EXPENSES - расходы
    @JsonProperty("finance_type")
    private FinanceType financeType;

    @Null
    private Long userId;

    @Null
    @Builder.Default
    private Instant AddedAt = Instant.now();
}
