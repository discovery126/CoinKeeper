package org.denis.coinkeeper.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.Services.UserService;
import org.denis.coinkeeper.api.dto.UserAuthDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.dto.UserFinanceDto;
import org.denis.coinkeeper.api.dto.UserSummaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public static final String USER_API_ENDPOINT = "/api/user";

    @PostMapping("/register")
    public ResponseEntity<UserSummaryDto> register(@RequestBody @Valid UserAuthDto userAuthDto){

        UserSummaryDto userSummaryDto = userService.register(userAuthDto);

        return ResponseEntity
                .created(URI.create(USER_API_ENDPOINT))
                .body(userSummaryDto);
    }


    @GetMapping
    public ResponseEntity<UserSummaryDto> getUser(Authentication authorization) {
        String email = authorization.getName();

        UserDto user = userService.getUser(email);
        UserFinanceDto userFinance = userService.getFinanceUser(email);

        return ResponseEntity
                .ok(UserSummaryDto.builder()
                        .userDto(user)
                        .userFinanceDto(userFinance)
                        .build());
    }


    @PutMapping
    public ResponseEntity<Void> putUserProfile(@RequestBody UserDto userDto,
                                             Authentication authorization) {
        String email = authorization.getName();

        userService.putUser(email,userDto);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtoList = userService.getUsers();
        return ResponseEntity
                .ok(userDtoList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authorization) {
        String email = authorization.getName();

        userService.removeUser(email);

        return ResponseEntity
                .noContent()
                .build();
    }
}