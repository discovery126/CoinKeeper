package org.denis.coinkeeper.api.services;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.factories.CurrencyDtoFactory;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyDtoFactory currencyDtoFactory;

    public void createCurrency(CurrencyDto currencyDto) {

        CurrencyEntity currencyEntity = CurrencyEntity.builder()
                .currencyName(currencyDto.getCurrencyName())
                .currencyDescription(currencyDto.getCurrencyDescription())
                .build();
        currencyRepository.save(currencyEntity);
    }

    public List<CurrencyDto> getCurrencyDtoList() {

        return currencyRepository.streamAllBy()
                .map(currencyDtoFactory::makeCurrencyDto)
                .collect(Collectors.toList());
    }

    public CurrencyDto getCurrencyById(Long currencyId) {

        CurrencyEntity currencyEntity = currencyRepository.getCurrencyEntityByCurrencyId(currencyId);
        if (currencyEntity != null) {
            return currencyDtoFactory.makeCurrencyDto(currencyEntity);
        }
        else {
            throw new BadRequestException("this currency not exist");
        }
    }

    public void putCurrency(Long currencyId,
                            CurrencyDto currencyDto) {
        CurrencyEntity currencyEntity = currencyRepository.getCurrencyEntityByCurrencyId(currencyId);
        if (currencyEntity != null) {
            if (!currencyEntity.getCurrencyName().equals(currencyDto.getCurrencyName())) {
                currencyEntity.setCurrencyName(currencyDto.getCurrencyName());
            }
            if (!currencyEntity.getCurrencyDescription().equals(currencyDto.getCurrencyDescription())) {
                currencyEntity.setCurrencyDescription(currencyDto.getCurrencyDescription());
            }
            currencyRepository.save(currencyEntity);
        }
        else {
            throw new BadRequestException("this currency not exist");
        }
    }

    public void deleteCurrencyById(Long currencyId) {
        currencyRepository.deleteById(currencyId);
    }

}
