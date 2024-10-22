package org.denis.coinkeeper.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.denis.coinkeeper.api.validation.CurrencyIsNotNull;
import org.denis.coinkeeper.api.validation.EmailUnique;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Null
    private Long userId;

    private Long account;

    @EmailUnique
    @NotNull
    @Email(message = "email: invalid format")
    private String email;

    @CurrencyIsNotNull
    private CurrencyDto currency;

}
