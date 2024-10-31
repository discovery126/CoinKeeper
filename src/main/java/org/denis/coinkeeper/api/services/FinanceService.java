package org.denis.coinkeeper.api.services;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.convertors.FinanceConvertor;
import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.FinanceType;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.repositories.FinanceRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;
    private final FinanceConvertor financeConvertor;

    private final UserRepository userRepository;

    @Transactional
    public void createFinance(String email,
                              FinanceDto financeDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        UserEntity userEntity = userEntityOptional.get();
        FinanceEntity financeEntity = FinanceEntity.builder()
                .name(financeDto.getName())
                .price(financeDto.getPrice())
                .category(financeDto.getCategory())
                .financeType(financeDto.getFinanceType().name())
                .build();
        userEntity.addFinance(financeEntity);
        userRepository.save(userEntity);
    }
    @Transactional(readOnly = true)
    public List<FinanceDto> getAllFinance(String email,
                                          FinanceType financeType,
                                          String period) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        UserEntity userEntity = userEntityOptional.get();
        LocalDateTime startDate = LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endDate = LocalDateTime.now(ZoneId.of("UTC"));
        startDate = getStartDate(period, startDate);

        List<FinanceEntity> financeEntityList;

        if (period.equals("all")) {
            if (financeType == null) {
                financeEntityList = financeRepository.findAllByUserOrderByAddedAtDesc(userEntity);
            } else {
                financeEntityList = financeRepository
                        .findAllByUserAndFinanceTypeOrderByAddedAtDesc(userEntity,financeType.name());
            }
        } else if (financeType == null) {
            financeEntityList = financeRepository
                    .findByUserAndAddedAtBetweenOrderByAddedAt(userEntity,
                            startDate,
                            endDate);

        } else {
            financeEntityList = financeRepository
                    .findByUserAndFinanceTypeAndAddedAtBetweenOrderByAddedAt(userEntity,
                            financeType.name(),
                            startDate,
                            endDate);
        }

        return financeEntityList
                .stream()
                .map(financeConvertor::makeFinanceDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public FinanceDto getFinanceById(String email,
                                     Long financeId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        FinanceEntity financeEntity =
                financeRepository.findFinanceEntitiesByUserAndFinanceId(userEntityOptional.get(),financeId);
        if (financeEntity == null) {
            throw new BadRequestException("This finance has not been found");
        }
        return financeConvertor.makeFinanceDto(financeEntity);

    }
    @Transactional
    public void putFinance(String email,
                           Long financeId,
                           FinanceDto financeDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("This user has not been found");
        }
        UserEntity userEntity = userEntityOptional.get();
        FinanceEntity financeEntityOrigin = financeRepository
                .findFinanceEntitiesByUserAndFinanceId(userEntity,financeId);
        FinanceEntity financeEntity = FinanceEntity.builder()
                .name(financeDto.getName())
                .category(financeDto.getCategory())
                .price(financeDto.getPrice())
                .addedAt(financeDto.getAddedAt())
                .build();
        if (financeEntityOrigin == null) {
            throw new BadRequestException("This finance has not been found");
        }
        if (!financeEntityOrigin.getName().equals(financeEntity.getName())) {
            financeEntityOrigin.setName(financeEntity.getName());
        } else if (!financeEntityOrigin.getCategory().equals(financeEntity.getCategory())) {
            financeEntityOrigin.setCategory(financeEntity.getCategory());
        } else if (!financeEntityOrigin.getPrice().equals(financeEntity.getPrice())) {
            financeEntityOrigin.setPrice(financeEntity.getPrice());
        }
        financeRepository.save(financeEntityOrigin);

    }
    @Transactional
    public void removeProfitById(String email,
                                 Long financeId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            FinanceEntity financeEntity = financeRepository.findFinanceEntitiesByUserAndFinanceId(user,financeId);
            if (financeEntity == null) {
                throw new BadRequestException("This finance has not been found");
            }
            user.removeFinance(financeEntity);
        } else {
            throw new BadRequestException("This user has not been found");
        }

    }
    private LocalDateTime getStartDate(String period,
                                       LocalDateTime startDate) {
        return switch (period) {
            case "today", "all" -> startDate;
            case "day" -> startDate.minusDays(1);
            case "week" -> startDate.minusDays(7);
            case "month" -> startDate.minusMonths(1);
            case "year" -> startDate.minusYears(1);
            default -> throw new BadRequestException("Invalid period");
        };
    }

}
