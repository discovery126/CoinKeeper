package org.denis.coinkeeper.api.convertors;

import org.denis.coinkeeper.api.dto.AuthorityDto;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.dto.UserDtoWithRoles;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

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
    public UserDtoWithRoles makeUserDtoWithRoles(UserEntity userEntity, Set<AuthorityDto> authorities) {
        return UserDtoWithRoles.builder()
                .userDto(this.makeUserDto(userEntity))
                .authorityDto(authorities)
                .build();
    }
}
