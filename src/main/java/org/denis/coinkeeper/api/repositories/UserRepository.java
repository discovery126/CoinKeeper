package org.denis.coinkeeper.api.repositories;

import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Stream<UserEntity> streamAllBy();
    Optional<UserEntity> findByEmail(String email);
}
