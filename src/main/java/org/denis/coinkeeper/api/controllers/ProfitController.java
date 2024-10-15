package org.denis.coinkeeper.api.controllers;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.Services.ProfitService;
import org.denis.coinkeeper.api.dto.ProfitDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/profit")
public class ProfitController {


    private static final String ENDPOINT_PATH = "/api/v1/profit";

    private final ProfitService profitService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ProfitDto> createProfit(@RequestBody ProfitDto profitDto,
                                           Authentication authorization) {
        String email = authorization.getName();

        profitService.createProfit(profitDto,email);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfitDto>> getProfits(Authentication authorization) {
        String email = authorization.getName();

        return ResponseEntity
                .ok(profitService.getProfits(email));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfitDto> putProfit(@RequestBody ProfitDto profitDto,
                                               @PathVariable("id") Long profitId,
                                               Authentication authorization) {
        String email = authorization.getName();

        ProfitDto profitDtoResult = profitService.putProfit(profitId,profitDto,email);

        return ResponseEntity
                .ok(profitDtoResult);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfitDto> getProfit(@PathVariable("id") Long profitId,
                                               Authentication authorization) {
        String email = authorization.getName();

        return ResponseEntity
                .ok(profitService.getProfitById(profitId,email));

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> removeProfit(@PathVariable("id") Long profitId,
                                               Authentication authorization) {
        String email = authorization.getName();
        profitService.removeProfitById(profitId,email);

        return ResponseEntity
                .noContent()
                .build();

    }
}
