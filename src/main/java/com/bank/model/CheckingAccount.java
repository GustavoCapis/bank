package main.java.com.bank.model;

import main.java.com.bank.exception.InsufficientBalanceException;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, Client holder, AccountType type, double overdraftLimit) {
        super(accountNumber, holder, type);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        double totalAvailable = balance + overdraftLimit;
        if (totalAvailable < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        this.balance -= amount;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
