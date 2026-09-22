package main.java.com.bank.service;

import main.java.com.bank.model.Account;
import main.java.com.bank.repository.AccountRepository;
import main.java.com.bank.exception.AccountNotFoundException;

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

    public void deposit(double amount, String accountNumber) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        Account accountExists = accountRepository.findByNumber(accountNumber);
        if (accountExists == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        accountExists.setBalance(accountExists.getBalance() + amount);
        accountRepository.save(accountExists);
        System.out.println("Deposit of " + amount + " to account " + accountExists.getAccountNumber() + " made successfully!");
    }

    public void withdraw(double amount, String accountNumber) {
        Account accountExists = accountRepository.findByNumber(accountNumber);
        if (accountExists == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        double balance = accountExists.getBalance();

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        } else if (amount > balance) {
            throw new IllegalArgumentException("Amount must be less than or equal to balance.");
        } else {
            accountExists.setBalance(accountExists.getBalance() - amount);
            accountRepository.save(accountExists);
            System.out.println("Withdraw of " + amount + " from account " + accountExists.getAccountNumber() + " made successfully!");
        }
    }

}
