package org.denis.coinkeeper.api.controllers;

import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.Services.ExpensesService;
import org.denis.coinkeeper.api.dto.ExpensesDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
//TODO:  timur: Я бы  объединил ExpensesController и ProfitController
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/expenses")
public class ExpensesController {

    private static final String ENDPOINT_PATH = "/api/v1/expenses";

    private final ExpensesService expensesService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> createExpenses(@RequestBody ExpensesDto expensesDto,
                                           Authentication authorization) {
        String email = authorization.getName();

        expensesService.createExpenses(expensesDto,email);

        return ResponseEntity
                .created(URI.create(ENDPOINT_PATH))
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExpensesDto>> getAllExpenses(Authentication authorization) {
        String email = authorization.getName();

        return ResponseEntity
                .ok(expensesService.getAllExpenses(email));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExpensesDto> putExpenses(@RequestBody ExpensesDto expensesDto,
                                                 @PathVariable("id") Long expensesId,
                                                 Authentication authorization) {
        String email = authorization.getName();

        ExpensesDto expensesDtoResult = expensesService.putExpenses(expensesId,expensesDto,email);

        return ResponseEntity
                .ok(expensesDtoResult);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExpensesDto> getExpenses(@PathVariable("id") Long expensesId,
                                               Authentication authorization) {
        String email = authorization.getName();

        return ResponseEntity
                .ok(expensesService.getExpensesById(expensesId,email));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> removeExpenses(@PathVariable("id") Long expensesId,
                                          Authentication authorization) {
        String email = authorization.getName();
        expensesService.removeExpensesById(expensesId,email);

        return ResponseEntity
                .noContent()
                .build();
    }
}
