package org.denis.coinkeeper.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.security.SecuritySessionContext;
import org.denis.coinkeeper.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private static final String ENDPOINT_PATH = "/api/v1/user";

    private final UserService userService;

    private final SecuritySessionContext securitySessionContext;

    @GetMapping
    public ResponseEntity<UserDto> getUser() {

        String email = securitySessionContext.getCurrentUserName();

        UserDto user = userService.getUser(email);

        return ResponseEntity
                .ok(user);
    }
    @PutMapping
    public ResponseEntity<Void> putUser(@RequestBody @Valid UserDto userDto) {

        String email = securitySessionContext.getCurrentUserName();

        userService.putUser(email, userDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }

    //TODO: timur:    Такие ресурсы не должны торчать наружу для обычных пользователей ( Это должно быть под ролью ADMIN)
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtoList = userService.getUsers();
        return ResponseEntity
                .ok(userDtoList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        
        String email = securitySessionContext.getCurrentUserName();

        userService.removeUser(email);

        return ResponseEntity
                .noContent()
                .build();
    }
}