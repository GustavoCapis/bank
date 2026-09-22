package main.java.com.bank.service;

import main.java.com.bank.model.Account;
import main.java.com.bank.repository.AccountRepository;
import main.java.com.bank.repository.InMemoryAccountRepository;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) {
        Account accountExists = accountRepository.findByNumber(account.getAccountNumber());
        if (accountExists != null) {
            System.out.println("Account already exists.");
            return;
        }
        accountRepository.save(account);
        System.out.println("Account created successfully!");
    }
}
