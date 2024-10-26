package org.denis.coinkeeper.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.denis.coinkeeper.api.validation.EmailUnique;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@Builder
public class RegisterDto {

    //TODO: timur:  Еще я бы добавил поле login или username - он мог бы совпадать с email по желанию пользователя  )
    @EmailUnique
    @NotNull
    @Email(message = "email: invalid format")
    private String email;

    @NotNull
    @JsonProperty("authority")
    private Set<AuthorityDto> authorityEntitySet;

    @NotNull
    @Length(min = 6, max = 30, message = "password: length must be more than 6 and less than 30 symbols")
    private String password;
}