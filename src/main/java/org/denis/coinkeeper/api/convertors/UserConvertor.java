package org.denis.coinkeeper.api.convertors;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {

    public UserDto makeUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .userId(userEntity.getUserId())
                .account(userEntity.getAccount())
                .email(userEntity.getEmail())
                .currency(CurrencyDto.builder()
                        .currencyId(userEntity.getCurrency().getCurrencyId())
                        .currencyName(userEntity.getCurrency().getCurrencyName())
                        .build())
                .build();
    }
}
