package main.java.com.bank.repository;

import main.java.com.bank.model.Account;
import java.util.List;

public interface AccountRepository {
    void save(Account account);
    Account findByNumber(String accountNumber);
    List<Account> findAll();
    void updateBalance(Account account);
}
