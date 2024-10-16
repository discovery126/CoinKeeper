package org.denis.coinkeeper.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.Services.CurrencyService;
import org.denis.coinkeeper.api.Services.UserService;
import org.denis.coinkeeper.api.dto.*;
import org.denis.coinkeeper.api.security.SecuritySessionContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private static final String ENDPOINT_PATH = "/api/v1/user";

    private final UserService userService;
    private final CurrencyService currencyService;

    private final SecuritySessionContext securitySessionContext;

    //TODO: timur:  В идеале регистрацию стоило бы перенести на другой контроллер
    @PostMapping("/register")
    public ResponseEntity<UserSummaryDto> register(@RequestBody @Valid UserAuthDto userAuthDto) {

        final UserSummaryDto userSummaryDto = userService.register(userAuthDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .body(userSummaryDto);
    }

    @GetMapping
    public ResponseEntity<UserSummaryDto> getUser() {

        String email = securitySessionContext.getCurrentUserName();

        UserDto user = userService.getUser(email);
        UserFinanceDto userFinance = userService.getFinanceUser(email);

        return ResponseEntity
                .ok(UserSummaryDto.builder()
                        .userDto(user)
                        .userFinanceDto(userFinance)
                        .build());
    }

    @PutMapping
    public ResponseEntity<Void> putUserProfile(@RequestBody UserDto userDto) {

        String email = securitySessionContext.getCurrentUserName();

        userService.putUser(email, userDto);

        return ResponseEntity
                .noContent()
                .build();
    }

    //TODO: timur:    Такие ресурсы не должны торчать наружу для обычных пользователей ( Это должно быть под ролью ADMIN)
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtoList = userService.getUsers();
        return ResponseEntity
                .ok(userDtoList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        
        String email = securitySessionContext.getCurrentUserName();

        userService.removeUser(email);

        return ResponseEntity
                .noContent()
                .build();
    }

    //TODO: timur:  Нужно перенести в другой контроллер -  что-то типа MasterDataLookupController
    @GetMapping("/currency")
    public ResponseEntity<List<CurrencyDto>> getCurrencies() {

        List<CurrencyDto> currencyDtoList = currencyService.getCurrencyDtoList();

        return ResponseEntity
                .ok(currencyDtoList);
    }
}