package org.denis.coinkeeper.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.denis.coinkeeper.api.validation.EmailUnique;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class RegisterDto {

    @EmailUnique
    @NotNull
    @Email(message = "email: invalid format")
    private String email;

    @NotNull
    @Length(min = 6, max = 30, message = "password: length must be more than 6 and less than 30 symbols")
    private String password;
}