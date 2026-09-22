package main.java.com.bank.model;

public abstract class Account {
    private String accountNumber;
    private Client holder;
    protected double balance;

    public Account(String accountNumber, Client holder) {
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getHolder() {
        return holder;
    }

    public void setHolder(Client holder) {
        this.holder = holder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        } else {
            this.balance += amount;
        }
    }
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        } else if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        } else {
            this.balance -= amount;
        }
    }

    @Override
    public String toString() {
        return "Account Number: '" + accountNumber + '\'' +
                ", Holder: " + holder +
                ", Balance: " + balance;
    }
}
