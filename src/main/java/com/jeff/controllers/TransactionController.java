package com.jeff.controllers;

import com.jeff.dtos.TransactionRequestDto;
import com.jeff.dtos.TransactionResponseDto;
import com.jeff.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponseDto> getAllTransactions() {
        return transactionService.findAllTransactions();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable UUID id) {
        TransactionResponseDto transaction = transactionService.findTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    @PostMapping
    public TransactionResponseDto createTransaction(@RequestBody TransactionRequestDto requestDto) {
        return transactionService.createTransaction(requestDto);
    }
    
    @PutMapping("/{id}")
    public TransactionResponseDto updateTransaction(@PathVariable UUID id, @RequestBody TransactionRequestDto requestDto) {
        return transactionService.updateTransaction(id, requestDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}