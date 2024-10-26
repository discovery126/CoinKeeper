package org.denis.coinkeeper.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.CurrencyDto;
import org.denis.coinkeeper.api.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/v1/currency")
public class CurrencyController {

    private static final String ENDPOINT_PATH = "/api/v1/currency";

    private final CurrencyService currencyService;


    @PostMapping
    public ResponseEntity<Void> createCurrency(@RequestBody CurrencyDto currencyDto) {

        currencyService.createCurrency(currencyDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
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

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDto> putCurrency(@PathVariable Long id,
                                                   @RequestBody CurrencyDto currencyDto){

        currencyService.putCurrency(id, currencyDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id){

        currencyService.deleteCurrencyById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
