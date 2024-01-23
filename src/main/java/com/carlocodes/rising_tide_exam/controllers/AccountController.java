package com.carlocodes.rising_tide_exam.controllers;

import com.carlocodes.rising_tide_exam.dtos.CreateAccountResponseDto;
import com.carlocodes.rising_tide_exam.dtos.CustomerDto;
import com.carlocodes.rising_tide_exam.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponseDto> createAccount(@RequestBody CustomerDto customerDto) throws Exception {
        return ResponseEntity.ok(accountService.createAccount(customerDto));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerAccounts(@PathVariable Long customerId) throws Exception {
        return ResponseEntity.ok(accountService.getCustomerAccounts(customerId));
    }
}
