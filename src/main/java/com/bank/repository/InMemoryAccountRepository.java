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
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }
}
