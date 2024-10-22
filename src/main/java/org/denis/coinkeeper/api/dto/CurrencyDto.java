package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Null;
import lombok.*;

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

}
