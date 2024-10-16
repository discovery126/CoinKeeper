package org.denis.coinkeeper.api.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.ExpensesDto;
import org.denis.coinkeeper.api.entities.ExpensesEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.factories.ExpensesDtoFactory;
import org.denis.coinkeeper.api.repositories.ExpensesRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO:  timur: Я бы  объединил ExpensesService и ProfitService - они делают одно и то же
@Service
@RequiredArgsConstructor
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final ExpensesDtoFactory expensesDtoFactory;

    private final UserRepository userRepository;

    @Transactional
    public void createExpenses(ExpensesDto expensesDto, String email) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            ExpensesEntity expensesEntity = ExpensesEntity.builder()
                    .name(expensesDto.getName())
                    .price(expensesDto.getPrice())
                    .category(expensesDto.getCategory())
                    .build();

            userEntity.addExpenses(expensesEntity);

            userRepository.save(userEntity);
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }

    public List<ExpensesDto> getAllExpenses(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            List<ExpensesEntity> expensesEntityList = userEntity.getExpensesList();

            return expensesEntityList
                    .stream()
                    .map(expensesDtoFactory::makeExpensesDto)
                    .toList();
        }
        else {
            throw new BadRequestException("This user not found");
        }
    }
    public ExpensesDto getExpensesById(Long expensesId,
                                   String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            ExpensesEntity expensesEntity = expensesRepository.findProfitEntitiesByExpensesIdAndUser(expensesId, userEntityOptional.get());
            if (expensesEntity != null) {
                return expensesDtoFactory.makeExpensesDto(expensesEntity);
            } else {
                throw new BadRequestException("This id expenses not found");
            }
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }

    @Transactional
    public ExpensesDto putExpenses(Long expensesId,
                                 ExpensesDto expensesDto,
                                 String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {

            UserEntity userEntity = userEntityOptional.get();

            ExpensesEntity expensesDtoOrigin = expensesRepository.findProfitEntitiesByExpensesIdAndUser(expensesId,userEntity);

            ExpensesEntity expensesEntity = ExpensesEntity.builder()
                    .name(expensesDto.getName())
                    .category(expensesDto.getCategory())
                    .price(expensesDto.getPrice())
                    .AddedAt(expensesDto.getAddedAt())
                    .build();

            if (expensesDtoOrigin != null) {
                if (!expensesDtoOrigin.getName().equals(expensesEntity.getName())) {
                    expensesDtoOrigin.setName(expensesEntity.getName());
                }
                else if (!expensesDtoOrigin.getCategory().equals(expensesEntity.getCategory())) {
                    expensesDtoOrigin.setCategory(expensesEntity.getCategory());
                }
                else if (!expensesDtoOrigin.getPrice().equals(expensesEntity.getPrice())) {
                    expensesDtoOrigin.setPrice(expensesEntity.getPrice());
                }
                expensesRepository.save(expensesDtoOrigin);

                return expensesDtoFactory.makeExpensesDto(expensesDtoOrigin);
            }
            else {
                throw new BadRequestException("This expenses is not found");
            }
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }
    @Transactional
    public void removeExpensesById(Long expensesId,
                                 String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            ExpensesEntity expensesEntity = expensesRepository.findProfitEntitiesByExpensesIdAndUser(expensesId, user);
            if (expensesEntity != null) {
                user.removeExpenses(expensesEntity);
            } else {
                throw new BadRequestException("This id expenses not found");
            }
        }

    }
}
