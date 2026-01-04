package com.jeff.controllers;

import com.jeff.dtos.LedgerEntryResponseDto;
import com.jeff.entities.LedgerEntry;
import com.jeff.repositories.LedgerEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ledger-entries")
public class LedgerEntryController {
    
    private final LedgerEntryRepository ledgerEntryRepo;
    
    public LedgerEntryController(LedgerEntryRepository ledgerEntryRepo) {
        this.ledgerEntryRepo = ledgerEntryRepo;
    }

    @GetMapping
    public List<LedgerEntryResponseDto> getAllLedgerEntries() {
        return ledgerEntryRepo.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public LedgerEntryResponseDto getLedgerEntryById(@PathVariable UUID id) {
        LedgerEntry ledgerEntry = ledgerEntryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("LedgerEntry not found"));
        return toResponseDto(ledgerEntry);
    }
    
    private LedgerEntryResponseDto toResponseDto(LedgerEntry ledgerEntry) {
        return new LedgerEntryResponseDto(
                ledgerEntry.getEntryId(),
                ledgerEntry.getEntryType(),
                ledgerEntry.getTransaction().getTransactionId(),
                ledgerEntry.getAccount().getAccountId(),
                ledgerEntry.getAccount().getHolderName(),
                ledgerEntry.getAmount(),
                ledgerEntry.getCreatedAt()
        );
    }
}