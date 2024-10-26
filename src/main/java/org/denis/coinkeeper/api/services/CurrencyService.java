package org.denis.coinkeeper.api.services;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.entities.CurrencyEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.convertors.CurrencyConvertor;
import org.denis.coinkeeper.api.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyConvertor currencyConvertor;


    @Transactional
    public void createCurrency(CurrencyDto currencyDto) {

        CurrencyEntity currencyEntity = CurrencyEntity.builder()
                .currencyName(currencyDto.getCurrencyName())
                .build();
        currencyRepository.save(currencyEntity);
    }

    @Transactional(readOnly = true)
    public List<CurrencyDto> getCurrencyDtoList() {

        return currencyRepository.streamAllBy()
                .map(currencyConvertor::makeCurrencyDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyById(Long currencyId) {

        CurrencyEntity currencyEntity = currencyRepository.getCurrencyEntityByCurrencyId(currencyId);
        if (currencyEntity != null) {
            return currencyConvertor.makeCurrencyDto(currencyEntity);
        }
        else {
            throw new BadRequestException("this currency not exist");
        }
    }

    @Transactional
    public void putCurrency(Long currencyId,
                            CurrencyDto currencyDto) {
        CurrencyEntity currencyEntity = currencyRepository.getCurrencyEntityByCurrencyId(currencyId);
        if (currencyEntity != null) {
            if (!currencyEntity.getCurrencyName().equals(currencyDto.getCurrencyName())) {
                currencyEntity.setCurrencyName(currencyDto.getCurrencyName());
            }
            currencyRepository.save(currencyEntity);
        }
        else {
            throw new BadRequestException("this currency not exist");
        }
    }

    @Transactional
    public void deleteCurrencyById(Long currencyId) {
        currencyRepository.deleteById(currencyId);
    }

}
