package org.denis.coinkeeper.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.convertors.AuthorityConvertor;
import org.denis.coinkeeper.api.convertors.UserConvertor;
import org.denis.coinkeeper.api.dto.RegisterDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.repositories.AuthorityRepository;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserConvertor userConvertor;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityConvertor authorityConvertor;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public UserDto register(RegisterDto registerDto) {

        String currencyDefault = "RUB";
        // Valid check is not null currency
        CurrencyEntity currencyEntity = currencyRepository.findByCurrencyName(currencyDefault).get();

        Set<AuthorityEntity> userAuthorityEntities =
                registerDto
                .getAuthorityEntitySet()
                .stream()
                .map(authDto -> authorityRepository.findAuthorityEntityByAuthorityName(authDto.getAuthorityName()))
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .currency(currencyEntity)
                .authorities(Collections.EMPTY_SET)
                .build();

        UserEntity resultEntity = userRepository.save(userEntity);

        return userConvertor.makeUserDto(resultEntity);
    }


    @Transactional
    public void putUser(String email, UserDto userDto) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();

            Optional<CurrencyEntity> currencyEntity = currencyRepository.findByCurrencyName(userDto.getCurrency().getCurrencyName());

            if (!userEntity1.getEmail().equals(userDto.getEmail())) {
                userEntity1.setEmail(userDto.getEmail());
            }
            if (!userEntity1.getCurrency().equals(currencyEntity.get())) {
                userEntity1.setCurrency(currencyEntity.get());
            }
            if (!userEntity1.getAccount().equals(userDto.getAccount())) {
                userEntity1.setAccount(userDto.getAccount());
            }
            userRepository.save(userEntity1);
        } else {
            throw new BadRequestException("This user has not been found");
        }
    }

    public UserDto getUser(String email) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty())
            throw new BadRequestException("This user has not been found");

        return userConvertor.makeUserDto(userEntityOptional.get());
    }

    @Transactional
    public List<UserDto> getUsers() {

        return userRepository.streamAllBy()
                .map(userConvertor::makeUserDto)
                .toList();
    }


    @Transactional
    public void removeUser(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        if (userEntityOptional.isEmpty())
            throw new BadRequestException("This user has not been found");

        userRepository.delete(userEntityOptional.get());
    }
}