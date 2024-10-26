package org.denis.coinkeeper.api.convertors;

import org.denis.coinkeeper.api.dto.AuthorityDto;
import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<AuthorityDto> makeAuthorityDtoSet(Set<AuthorityEntity> authorityEntitySet) {
        return  authorityEntitySet.stream()
                .map(this::makeAuthorityDto)
                .collect(Collectors.toSet());
    }
}
