package org.denis.coinkeeper.api.controllers;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.services.FinanceService;
import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.FinanceType;
import org.denis.coinkeeper.api.security.SecuritySessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/finance")
public class FinanceController {

    private static final String ENDPOINT_PATH = "/api/v1/finance";

    private final SecuritySessionContext securitySessionContext;

    private final FinanceService financeService;

    @PostMapping
    ResponseEntity<Void> createFinance(@RequestBody FinanceDto financeDto) {

        String email = securitySessionContext.getCurrentUserName();

        financeService.createFinance(email, financeDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<FinanceDto>> getAllFinance(@RequestParam(name = "type") FinanceType financeType) {
        String email = securitySessionContext.getCurrentUserName();
        List<FinanceDto> financeEntityList = financeService.getAllFinance(email,financeType.name());

        return ResponseEntity
                .ok(financeEntityList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceDto> getFinance(@PathVariable("id") Long financeId) {
        String email = securitySessionContext.getCurrentUserName();

        return ResponseEntity
                .ok(financeService.getFinanceById(email, financeId));

    }


    @PutMapping("/{id}")
    public ResponseEntity<FinanceDto> putFinance(@RequestBody FinanceDto financeDto,
                                                 @PathVariable("id") Long financeId) {
        String email = securitySessionContext.getCurrentUserName();

        financeService.putFinance(email, financeId, financeDto);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFinance(@PathVariable("id") Long financeId) {

        String email = securitySessionContext.getCurrentUserName();

        financeService.removeProfitById(email, financeId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
