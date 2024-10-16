package org.denis.coinkeeper.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

//TODO: timur:  Я бы переназвал класс в RegisterDto - ( тут могут появиться  новые поля)
@Data
@Builder
public class UserAuthDto {


    //TODO: timur:  Я бы добавил кастомный валидатор проверки уникальности email)
    //TODO: timur:  Еще я бы добавил поле login или username - он мог бы совпадать с email по желанию пользователя  )
    @NotNull
    @Email(message = "email: invalid format")
    private String email;

    @NotNull
    @Length(min = 6, max = 30, message = "password: length must be more than 6 and less than 30 symbols")
    private String password;
}