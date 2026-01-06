package com.jeff.services;

import com.jeff.dtos.AccountRequestDto;
import com.jeff.dtos.AccountResponseDto;
import com.jeff.entities.Account;
import com.jeff.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    // Fetch all accounts
    public List<AccountResponseDto> findAllAccounts() {
        return accountRepo.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // Find one account by ID
    public AccountResponseDto findAccountById(UUID id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account with id " + id + " not found"));
        return toResponseDto(account);
    }

    // Delete account by ID
    public void deleteAccountById(UUID id) {
        accountRepo.deleteById(id);
    }

    // Create a new account
    public AccountResponseDto createAccount(AccountRequestDto requestDto) {
        Account account = toEntity(requestDto);
        Account savedAccount = accountRepo.save(account);
        return toResponseDto(savedAccount);
    }

    // Update a specific account by ID
    public AccountResponseDto updateAccount(UUID id, AccountRequestDto requestDto) {
        Optional<Account> optionalAccount = accountRepo.findById(id);

        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();

            existingAccount.setHolderName(requestDto.getHolderName());
            existingAccount.setAccType(requestDto.getAccType());
            existingAccount.setBalance(requestDto.getBalance());

            Account savedAccount = accountRepo.save(existingAccount);
            return toResponseDto(savedAccount);
        } else {
            throw new RuntimeException("Account with id " + id + " not found");
        }
    }

    // Mapping methods
    private AccountResponseDto toResponseDto(Account account) {
        return new AccountResponseDto(
                account.getAccountId(),
                account.getHolderName(),
                account.getAccType(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }

    private Account toEntity(AccountRequestDto requestDto) {
        return new Account(
                requestDto.getHolderName(),
                requestDto.getAccType(),
                requestDto.getBalance()
        );
    }
}
