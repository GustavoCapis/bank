package main.java.com.bank.model;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, Client holder) {
        super(accountNumber, holder);
    }
    public void yieldInterest(double rate) {
        if (rate > 0) {
            double interest = rate * balance;
            deposit(interest);
        }
    }
}
