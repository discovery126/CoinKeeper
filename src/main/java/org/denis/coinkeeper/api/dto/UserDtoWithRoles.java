package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoWithRoles {

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("authority")
    private Set<AuthorityDto> authorityDto;
}
