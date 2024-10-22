package org.denis.coinkeeper.api.repositories;

import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntity,Long> {

    FinanceEntity findFinanceEntitiesByUserAndFinanceId(UserEntity user,Long financeId);
    Stream<FinanceEntity> streamAllByUserAndFinanceType(UserEntity user, String stringFinanceType);
}
