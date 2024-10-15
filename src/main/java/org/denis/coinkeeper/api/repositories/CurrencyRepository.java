package org.denis.coinkeeper.api.repositories;

import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Long> {

    Stream<CurrencyEntity> streamAllBy();
    CurrencyEntity getCurrencyEntityByCurrencyId(Long currencyId);
    Optional<CurrencyEntity> findByCurrencyName(String currencyName);
}
