package org.denis.coinkeeper.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.Services.CurrencyService;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;


    @PostMapping("/new")
    public ResponseEntity<?> createCurrency(@RequestBody CurrencyDto currencyDto) {

        currencyService.createCurrency(currencyDto);

        return ResponseEntity
                .created(URI.create("/currency"))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getCurrencies() {

        List<CurrencyDto> currencyDtoList = currencyService.getCurrencyDtoList();

        return ResponseEntity
                .ok(currencyDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDto> getCurrency(@PathVariable Long id){

        CurrencyDto currencyDto = currencyService.getCurrencyById(id);

        return ResponseEntity
                .ok(currencyDto);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<CurrencyDto> putCurrency(@PathVariable Long id,
                                                   @RequestBody CurrencyDto currencyDto){

        CurrencyDto currencyDtoResult = currencyService.putCurrencyById(id,currencyDto);

        return ResponseEntity
                .ok(currencyDtoResult);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id){

        currencyService.deleteCurrencyById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
