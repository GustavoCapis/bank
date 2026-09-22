package main.java.com.bank;

import main.java.com.bank.model.Account;
import main.java.com.bank.model.CheckingAccount;
import main.java.com.bank.model.Client;
import main.java.com.bank.repository.AccountRepository;
import main.java.com.bank.repository.InMemoryAccountRepository;
import main.java.com.bank.service.AccountService;

public class Main {
    public static void main(String[] args) {
        AccountRepository repository = new InMemoryAccountRepository();

        AccountService service = new AccountService(repository);

        Client client1 = new Client("Sabrina Carpenter", "111.444.222-89");

        Account account1 = new CheckingAccount("12345-6", client1, 1400);

        try {
            System.out.println("Trying to create account");
            service.createAccount(account1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Trying to make a deposit");
            service.deposit(500,"12345-6");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
