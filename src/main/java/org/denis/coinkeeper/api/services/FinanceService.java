package org.denis.coinkeeper.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.FinanceEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.factories.FinanceDtoFactory;
import org.denis.coinkeeper.api.repositories.FinanceRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;
    private final FinanceDtoFactory financeDtoFactory;

    private final UserRepository userRepository;

    @Transactional
    public void createFinance(String email, FinanceDto financeDto) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
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
        else {
            throw new BadRequestException("This user has not been found");
        }

    }
    @Transactional
    public List<FinanceDto> getAllFinance(String email, String stringFinanceType) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Stream<FinanceEntity> financeEntity = financeRepository.streamAllByUserAndFinanceType(userEntity, stringFinanceType);

            return financeEntity
                    .map(financeDtoFactory::makeFinanceDto)
                    .toList();
        }
        else {
            throw new BadRequestException("This user has not been found");
        }
    }

    public FinanceDto getFinanceById(String email,
                                     Long financeId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        if (userEntityOptional.isPresent()) {
            FinanceEntity financeEntity =
                    financeRepository.findFinanceEntitiesByUserAndFinanceId(userEntityOptional.get(), financeId);
            if (financeEntity == null) {
                throw new BadRequestException("This finance has not been found");
            }
            return financeDtoFactory.makeFinanceDto(financeEntity);
        }
        else {
            throw new BadRequestException("This user has not been found");
        }

    }

    @Transactional
    public void putFinance(String email,
                           Long financeId,
                           FinanceDto financeDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {

            UserEntity userEntity = userEntityOptional.get();

            FinanceEntity financeEntityOrigin = financeRepository.findFinanceEntitiesByUserAndFinanceId(userEntity,financeId);

            FinanceEntity financeEntity = FinanceEntity.builder()
                    .name(financeDto.getName())
                    .category(financeDto.getCategory())
                    .price(financeDto.getPrice())
                    .AddedAt(financeDto.getAddedAt())
                    .build();

            if (financeEntityOrigin != null) {
                if (!financeEntityOrigin.getName().equals(financeEntity.getName())) {
                    financeEntityOrigin.setName(financeEntity.getName());
                }
                else if (!financeEntityOrigin.getCategory().equals(financeEntity.getCategory())) {
                    financeEntityOrigin.setCategory(financeEntity.getCategory());
                }
                else if (!financeEntityOrigin.getPrice().equals(financeEntity.getPrice())) {
                    financeEntityOrigin.setPrice(financeEntity.getPrice());
                }
                financeRepository.save(financeEntityOrigin);
            }
            else {
                throw new BadRequestException("This finance has not been found");
            }
        }
        else {
            throw new BadRequestException("This user has not been found");
        }

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

}
