package org.denis.coinkeeper.api.services;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.convertors.AuthorityConvertor;
import org.denis.coinkeeper.api.convertors.UserConvertor;
import org.denis.coinkeeper.api.dto.RegisterDto;
import org.denis.coinkeeper.api.dto.UserDto;
import org.denis.coinkeeper.api.dto.UserDtoWithRoles;
import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.exceptions.ServerErrorException;
import org.denis.coinkeeper.api.repositories.AuthorityRepository;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        final String currencyDefault = "RUB";
        final String authorityDefault = "USER";

        final AuthorityEntity authorityEntityByDefault = authorityRepository.findAuthorityEntityByAuthorityName(authorityDefault);

        if (authorityEntityByDefault == null)
            throw new ServerErrorException("Error: Authority not found");

        // Valid check is not null currency
        CurrencyEntity currencyEntity = currencyRepository.findByCurrencyName(currencyDefault).get();

        Set<AuthorityEntity> userAuthorityEntities = Set.of(authorityEntityByDefault);

        UserEntity userEntity = UserEntity.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .currency(currencyEntity)
                .authorities(userAuthorityEntities)
                .build();

        UserEntity resultEntity = userRepository.save(userEntity);

        return userConvertor.makeUserDto(resultEntity);
    }


    @Transactional
    public void putUser(String email, UserDto userDto) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        UserEntity userEntity1 = userEntity.get();

        Optional<UserEntity> userEntityForChanges = userRepository.findByEmail(userDto.getEmail());

        if (userEntityForChanges.isPresent() && !userEntityForChanges.get().equals(userEntity1)) {
            throw new BadRequestException("Error");
        }

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
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String email) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty())
            throw new BadRequestException("This user has not been found");

        return userConvertor.makeUserDto(userEntityOptional.get());
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public UserDtoWithRoles newAdmin(UserDto userDto) {

        final String authorityAdmin = "ADMIN";
        final AuthorityEntity authorityEntityAdmin = authorityRepository.findAuthorityEntityByAuthorityName(authorityAdmin);

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userDto.getEmail());
        if (authorityEntityAdmin == null) {
            throw new ServerErrorException("Error: Authority not found");
        }
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.getAuthorities().add(authorityEntityAdmin);

        return userConvertor.makeUserDtoWithRoles(
                userRepository.save(userEntity),
                authorityConvertor.makeAuthorityDtoSet(userEntity.getAuthorities())
        );
    }
}