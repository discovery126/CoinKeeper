package org.denis.coinkeeper.api.services;

import lombok.AllArgsConstructor;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.repositories.FinanceRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FinanceCalculateService {

    private final UserRepository userRepository;
    private final FinanceRepository financeRepository;
    public Double calculateSumExpenses(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        return BigDecimal.valueOf(financeRepository.getSumExpenses(userEntityOptional.get()))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public Double calculateSumIncomes(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        return BigDecimal.valueOf(financeRepository.getSumIncomes(userEntityOptional.get()))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public Long calculateBalance(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        Double sumExpenses = financeRepository.getSumExpenses(userEntityOptional.get());
        Double sumIncomes = financeRepository.getSumIncomes(userEntityOptional.get());
        return BigDecimal.valueOf(sumIncomes - sumExpenses)
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
    }
}
