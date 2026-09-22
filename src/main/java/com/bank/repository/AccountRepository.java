package main.java.com.bank.repository;

import main.java.com.bank.model.Account;

import java.util.ArrayList;
import java.util.List;

public interface AccountRepository {
    void save(Account account);
    List<Account> findAll();
}
