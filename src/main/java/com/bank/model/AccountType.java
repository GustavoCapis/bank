package main.java.com.bank.model;

public enum AccountType {
    CHECKING("Checking Account"),
    SAVINGS("Savings Account");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
