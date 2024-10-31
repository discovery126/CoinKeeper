package org.denis.coinkeeper.api.repositories;

import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntity,Long> {

    FinanceEntity findFinanceEntitiesByUserAndFinanceId(UserEntity user,Long financeId);
    List<FinanceEntity> findAllByUserOrderByAddedAtDesc(UserEntity user);
    List<FinanceEntity> findAllByUserAndFinanceTypeOrderByAddedAtDesc(UserEntity user,String stringFinanceType);
    List<FinanceEntity> findByUserAndFinanceTypeAndAddedAtBetweenOrderByAddedAt(UserEntity user, String stringFinanceType, LocalDateTime a, LocalDateTime b);
    List<FinanceEntity> findByUserAndAddedAtBetweenOrderByAddedAt(UserEntity user, LocalDateTime a, LocalDateTime b);}
