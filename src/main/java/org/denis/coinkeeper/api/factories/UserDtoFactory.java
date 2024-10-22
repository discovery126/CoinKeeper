package org.denis.coinkeeper.api.factories;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// По сути это не фабрика, а преобразователь в Dto
public class UserDtoFactory {


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
