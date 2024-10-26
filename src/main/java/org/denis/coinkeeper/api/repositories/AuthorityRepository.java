package org.denis.coinkeeper.api.repositories;

import org.denis.coinkeeper.api.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Long> {
    AuthorityEntity findAuthorityEntityByAuthorityName(String authorityName);
}
