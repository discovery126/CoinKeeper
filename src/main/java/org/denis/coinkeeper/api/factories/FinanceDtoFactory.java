package org.denis.coinkeeper.api.factories;

import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.FinanceType;
import org.springframework.stereotype.Component;

@Component
public class FinanceDtoFactory {

    public FinanceDto makeFinanceDto(FinanceEntity financeEntity) {
        return FinanceDto.builder()
                .name(financeEntity.getName())
                .financeId(financeEntity.getFinanceId())
                .price(financeEntity.getPrice())
                .financeType(FinanceType.valueOf(financeEntity.getFinanceType()))
                .userId(financeEntity.getUser().getUserId())
                .category(financeEntity.getCategory())
                .AddedAt(financeEntity.getAddedAt())
                .build();
    }
}
