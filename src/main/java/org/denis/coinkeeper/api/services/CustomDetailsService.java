package org.denis.coinkeeper.api.services;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = this.userRepository.findByEmail(username);
        if (userOptional.isPresent()) {
            return userOptional
                    .map(userEntity -> User.builder()
                            .username(userEntity.getEmail())
                            .password(userEntity.getPassword())
                            .build()).get();
        } else {
            throw new UsernameNotFoundException("UserEntity %s not found".formatted(username));
        }
    }

}
