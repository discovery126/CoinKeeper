package org.denis.coinkeeper.api.convertors;

import org.denis.coinkeeper.api.dto.AuthorityDto;
import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.FinanceType;
import org.springframework.stereotype.Component;

@Component
public class AuthorityConvertor {

    public AuthorityDto makeAuthorityDto(AuthorityEntity authorityEntity) {
        return AuthorityDto.builder()
                .authorityId(authorityEntity.getAuthorityId())
                .authorityName(authorityEntity.getAuthorityName())
                .build();
    }
    public AuthorityEntity makeAuthorityEntity(AuthorityDto authorityDto) {
        return AuthorityEntity.builder()
                .authorityId(authorityDto.getAuthorityId())
                .authorityName(authorityDto.getAuthorityName())
                .build();
    }
}
