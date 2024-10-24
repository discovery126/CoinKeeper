package org.denis.coinkeeper.api.services;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity %s not found".formatted(username)));

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getAuthorities()
                        .stream()
                        .map(AuthorityEntity::getAuthorityName)
                        .map(SimpleGrantedAuthority::new)
                        .toList())
                .build();
        }
}
