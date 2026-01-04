package com.jeff.controllers;

import com.jeff.dtos.AccountRequestDto;
import com.jeff.dtos.AccountResponseDto;
import com.jeff.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    private final AccountService accountService;
    
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.findAllAccounts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable UUID id) {
        AccountResponseDto account = accountService.findAccountById(id);
        return ResponseEntity.ok(account);
    }
    
    @PostMapping
    public AccountResponseDto createAccount(@RequestBody AccountRequestDto requestDto) {
        return accountService.createAccount(requestDto);
    }
    
    @PutMapping("/{id}")
    public AccountResponseDto updateAccount(@PathVariable UUID id, @RequestBody AccountRequestDto requestDto) {
        return accountService.updateAccount(id, requestDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }
}
