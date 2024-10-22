package org.denis.coinkeeper.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.RegisterDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/register")
public class RegisterController {

    private final UserService userService;

    private static final String ENDPOINT_PATH = "/api/v1/user";

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto) {

        final UserDto userDto = userService.register(registerDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .body(userDto);
    }

}
