package org.denis.coinkeeper.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.RegisterDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.exceptions.ServerErrorException;
import org.denis.coinkeeper.api.factories.UserDtoFactory;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDtoFactory userDtoFactory;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public UserDto register(RegisterDto registerDto) {

        String currencyDefault = "RUB";
        // Valid check is not null currency
        CurrencyEntity currencyEntity = currencyRepository.findByCurrencyName(currencyDefault).get();

        UserEntity userEntity = UserEntity.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .currency(currencyEntity)
                .build();

        UserEntity resultEntity = userRepository.save(userEntity);

        return userDtoFactory.makeUserDto(resultEntity);
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

        return userDtoFactory.makeUserDto(userEntityOptional.get());
    }

    public List<UserDto> getUsers() {

        return userRepository.streamAllBy()
                .map(userDtoFactory::makeUserDto)
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