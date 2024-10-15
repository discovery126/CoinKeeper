package org.denis.coinkeeper.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {

    @NotNull
    @Email(message = "email: invalid format")
    private String email;

    @NotNull
    @Length(min = 6,max = 30,message = "password: length must be more than 6 and less than 30 symbols")
    private String password;
}
