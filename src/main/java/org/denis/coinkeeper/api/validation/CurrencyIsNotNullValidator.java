package org.denis.coinkeeper.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class CurrencyIsNotNullValidator implements ConstraintValidator<CurrencyIsNotNull, CurrencyDto> {

    private final CurrencyRepository currencyRepository;

    @Override
    public void initialize(CurrencyIsNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(CurrencyDto currencyDto, ConstraintValidatorContext context) {
        Optional<CurrencyEntity> currencyDtoOptional = currencyRepository.findByCurrencyName(currencyDto.getCurrencyName());

        return currencyDtoOptional.isPresent();
    }
}
