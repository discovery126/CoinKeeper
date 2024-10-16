package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    @JsonProperty("currency_id")
    @Null
    private Long currencyId;

    @JsonProperty("currency_name")
    private String currencyName;

    @JsonProperty("currency_description")
    private String currencyDescription;
}
