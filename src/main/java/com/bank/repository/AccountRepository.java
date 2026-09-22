package main.java.com.bank.repository;

import main.java.com.bank.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Account findByNumber(String accountNumber);
    List<Account> findAll();
}
