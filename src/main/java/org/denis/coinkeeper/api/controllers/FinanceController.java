package org.denis.coinkeeper.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.FinanceDto;
import org.denis.coinkeeper.api.entities.FinanceType;
import org.denis.coinkeeper.api.security.SecuritySessionContext;
import org.denis.coinkeeper.api.services.FinanceService;
import org.denis.coinkeeper.api.services.FinanceCalculateService;
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
    private final FinanceCalculateService financeCalculateService;

    @PostMapping
    ResponseEntity<Void> createFinance(@RequestBody FinanceDto financeDto) {
        String email = securitySessionContext.getCurrentUserName();
        financeService.createFinance(email, financeDto);
        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<FinanceDto>> getAllFinance(@RequestParam(name = "type",required = false) FinanceType financeType,
                                                          @RequestParam(name = "period",required = false, defaultValue = "today") String period) {
        String email = securitySessionContext.getCurrentUserName();
        List<FinanceDto> financeDtoList = financeService.getAllFinance(email,
                financeType,
                period);

        return ResponseEntity
                .ok(financeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceDto> getFinance(@PathVariable("id") Long financeId) {
        String email = securitySessionContext.getCurrentUserName();

        return ResponseEntity
                .ok(financeService.getFinanceById(email, financeId));

    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceDto> putFinance(@RequestBody @Valid FinanceDto financeDto,
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
    @GetMapping("/expenses")
    public ResponseEntity<Double> getSumExpenses() {
        String email = securitySessionContext.getCurrentUserName();

        return ResponseEntity
                .ok(financeCalculateService.calculateSumExpenses(email));
    }
    @GetMapping("/incomes")
    public ResponseEntity<Double> getSumIncomes() {
        String email = securitySessionContext.getCurrentUserName();

        return ResponseEntity
                .ok(financeCalculateService.calculateSumIncomes(email));
    }
}
