package org.denis.coinkeeper.api.controllers;

import org.denis.coinkeeper.api.Services.CurrencyService;
import org.denis.coinkeeper.api.Services.UserService;
import org.denis.coinkeeper.api.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    CurrencyService currencyService;

    @InjectMocks
    UserController userController;

    CurrencyDto currencyDto = CurrencyDto.builder()
            .currencyId(1L)
            .currencyName("RUB")
            .currencyDescription("Рубль — денежная единица России")
            .build();

    @Test
    @DisplayName("createUser, create new user and return his profile")
    void registerUser_RequestIsValid_ReturnsUserSummaryDto() {
        // given
        UserAuthDto authDto = UserAuthDto.builder()
                .email("den4k3@mail.ru")
                .password("123456")
                .build();

        UserDto userDto = UserDto.builder()
                .userId(1L)
                .currency(currencyDto)
                .account(100L)
                .email("den4k3@mail.ru")
                .build();

        UserFinanceDto userFinanceDto = UserFinanceDto.builder()
                .profitEntityList(Collections.emptyList())
                .expensesEntityList(Collections.emptyList())
                .build();

        UserSummaryDto userSummaryDto = UserSummaryDto.builder()
                .userDto(userDto)
                .userFinanceDto(userFinanceDto)
                .build();

        // when
        Mockito.when(userService.register(authDto)).thenReturn(userSummaryDto);
        var responseEntity = this.userController.register(authDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userSummaryDto, responseEntity.getBody());
    }
// TODO:  timur:  Нужно тестировать в Integration тестах

//    @Test
//    @DisplayName("createUser, invalid input")
//    void registerUser_RequestIsInvalid_ReturnsError() {
//        //given
//        UserAuthDto authDto = UserAuthDto.builder()
//                .email("denmail")
//                .password("den1234")
//                .build();
//
//        //when
//        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
//
//        //then
//        Assertions.assertThrows(BadRequestException.class,() ->
//                this.userController.register(authDto,bindingResult));
//    }
    @Test
    @DisplayName("getAllCurrency,y return all currencies")
    void getCurrency_ReturnsListCurrencyDto() {
        //given
        List<CurrencyDto> currencyDtoList = new ArrayList<>(Arrays.asList(
                new CurrencyDto(1L,"RUB", "Рубль — денежная единица России"),
                new CurrencyDto(2L,"USD", "Доллар — денежная единица США"),
                new CurrencyDto(3L,"EUR", "Евро — денюжная единица Европы")));

        //when
        Mockito.when(currencyService.getCurrencyDtoList()).thenReturn(currencyDtoList);
        var responseEntity = this.userController.getCurrencies();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(currencyDtoList, responseEntity.getBody());
    }

    @Test
    @DisplayName("getUser, return user information")
    void getUser_ReturnsUserDto() {
        //given
        Authentication authentication = mock(Authentication.class);

        String email = "den4k3@mail.ru";

        UserDto userDto = UserDto.builder()
                        .userId(1L)
                        .currency(currencyDto)
                        .account(100L)
                        .email(email)
                        .build();

        UserFinanceDto userFinanceDto = UserFinanceDto.builder()
                        .profitEntityList(Collections.emptyList())
                        .expensesEntityList(Collections.emptyList())
                        .build();

        UserSummaryDto userSummaryDto = UserSummaryDto.builder()
                .userDto(userDto)
                .userFinanceDto(userFinanceDto)
                .build();


        //when
        Mockito.when(authentication.getName()).thenReturn(email);
        Mockito.when(userService.getUser(email)).thenReturn(userDto);
        Mockito.when(userService.getFinanceUser(email)).thenReturn(userFinanceDto);

        var responseEntity = this.userController.getUser();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(userSummaryDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("putUser, return modifier user")
    void putUser_RequestIsValid_ReturnsModifierUserDto() {
        //given
        Authentication authentication = mock(Authentication.class);

        String email = "den4k3@mail.ru";

        UserDto userDto = UserDto.builder()
                .userId(1L)
                .currency(currencyDto)
                .account(100L)
                .email(email)
                .build();

        //when
        Mockito.when(authentication.getName()).thenReturn(email);

        var responseEntity = this.userController.putUserProfile(userDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("getAllUsers, return all users information")
    void getAllUsers_ReturnListUserDto() {
        //given
        List<UserDto> userDtoList = Arrays.asList(
                UserDto.builder()
                        .userId(1L)
                        .currency(currencyDto)
                        .account(100L)
                        .email("den4@mail.ru")
                        .build(),
                UserDto.builder()
                        .userId(2L)
                        .currency(currencyDto)
                        .account(150L)
                        .email("den4k@mail.ru")
                        .build()

        );

        //when
        Mockito.when(userService.getUsers()).thenReturn(userDtoList);

        var responseEntity = this.userController.getAllUsers();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(userDtoList, responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteUser, return response code NO CONTENT")
    void deleteUser_ReturnsStatusCodeNoContent() {
        Authentication authentication = mock(Authentication.class);

        String email = "den4k3@mail.ru";
        //given

        UserDto userDto =  UserDto.builder()
                .userId(1L)
                .currency(currencyDto)
                .account(100L)
                .email("den4@mail.ru")
                .build();
        //when
        Mockito.when(authentication.getName()).thenReturn(email);

        var responseEntity = this.userController.deleteUser();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

}