package org.denis.coinkeeper.api.controllers;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.dto.UserDtoWithRoles;
import org.denis.coinkeeper.api.security.SecuritySessionContext;
import org.denis.coinkeeper.api.services.FinancesCalculateService;
import org.denis.coinkeeper.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private static final String ENDPOINT_PATH = "/api/v1/user";
    private final UserService userService;
    private final FinancesCalculateService financesCalculateService;
    private final SecuritySessionContext securitySessionContext;

    @GetMapping
    public ResponseEntity<UserDto> getUser() {

        String email = securitySessionContext.getCurrentUserName();

        UserDto user = userService.getUser(email);

        return ResponseEntity
                .ok(user);
    }
    @PutMapping
    public ResponseEntity<Void> putUser(@RequestBody UserDto userDto) {

        String email = securitySessionContext.getCurrentUserName();

        userService.putUser(email, userDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {

        String email = securitySessionContext.getCurrentUserName();

        userService.removeUser(email);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/newAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDtoWithRoles> createNewAdmin(@RequestBody UserDto userDto) {

        UserDtoWithRoles userDtoWithRoles = userService.newAdmin(userDto);
        return ResponseEntity
                .ok(userDtoWithRoles);
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtoList = userService.getUsers();
        return ResponseEntity
                .ok(userDtoList);
    }

    @GetMapping("/balance")
    public ResponseEntity<Long> getBalance() {
        String email = securitySessionContext.getCurrentUserName();
        return ResponseEntity
                .ok(financesCalculateService.calculateBalance(email));
    }
}