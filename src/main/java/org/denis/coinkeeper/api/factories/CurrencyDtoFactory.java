package org.denis.coinkeeper.api.factories;

import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDtoFactory {

    public CurrencyDto makeCurrencyDto(CurrencyEntity currencyEntity) {
        return CurrencyDto.builder()
                .currencyId(currencyEntity.getCurrencyId())
                .currencyName(currencyEntity.getCurrencyName())
                .build();
    }
}
