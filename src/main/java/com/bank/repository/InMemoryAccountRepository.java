package main.java.com.bank.repository;

import main.java.com.bank.model.Account;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountRepository implements AccountRepository {
    private List<Account> accounts = new ArrayList<>();

    @Override
    public void save(Account account) {
        accounts.add(account);
    }

    @Override
    public Account findByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }
}

